package net.purplemushroom.everend.content.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.client.registry.EEMusic;
import net.purplemushroom.everend.packet.SBossInfoPacket;
import net.purplemushroom.everend.registry.EEPackets;
import net.purplemushroom.everend.util.EntityUtil;
import net.purplemushroom.everend.util.EverendBossInfo;
import net.purplemushroom.everend.util.ai.EverendMeleeAttack;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class EnderLord extends Monster implements NeutralMob, IEverendBoss<EnderLord> {
    private static final EntityDataAccessor<Boolean> DATA_CREEPY = SynchedEntityData.defineId(EnderLord.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(EnderLord.class, EntityDataSerializers.BOOLEAN);
    private int lastStareSound = Integer.MIN_VALUE;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    private int teleportCount = 10;
    private boolean isDoingBullethell = false;

    private final ServerBossEvent barInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS);

    public EnderLord(EntityType<? extends EnderLord> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnderLordBulletHellGoal(this));
        this.goalSelector.addGoal(2, new EverendMeleeAttack(this, 1.0D, 0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    /**
     * Sets the active target the Goal system uses for tracking
     */
    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        if (pLivingEntity == null) {
            this.entityData.set(DATA_CREEPY, false);
            this.entityData.set(DATA_STARED_AT, false);
        } else {
            this.entityData.set(DATA_CREEPY, true);
        }

        super.setTarget(pLivingEntity); //Forge: Moved down to allow event handlers to write data manager values.
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CREEPY, false);
        this.entityData.define(DATA_STARED_AT, false);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int pTime) {
        this.remainingPersistentAngerTime = pTime;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
            }
        }

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_CREEPY.equals(pKey) && this.hasBeenStaredAt() && this.level().isClientSide) {
            this.playStareSound();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        this.addPersistentAngerSaveData(pCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        this.readPersistentAngerSaveData(this.level(), pCompound);

        if (this.hasCustomName()) {
            this.barInfo.setName(this.getDisplayName());
        }
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 2.55F;
    }

    /**
     * Called every tick so the entity can update its state as required. For example, zombies and skeletons use this to
     * react to sunlight and start to burn.
     */
    public void aiStep() {
        if (this.level().isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }

        this.jumping = false;
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }

        super.aiStep();
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        barInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    /**
     * Teleport the enderman to a random nearby position
     */
    protected boolean teleportInResponseToAttack(boolean wasRanged) {
        if (isDoingBullethell()) return false;
        if (!this.level().isClientSide() && this.isAlive()) {
            LivingEntity target = getTarget();
            if (target!= null) {
                Vec3 currentPos = position();
                Vec3 origin = target.position();
                for (int i = 0; i < 16; i++) {
                    float angle = this.random.nextFloat() * Mth.TWO_PI;
                    double magnitude = wasRanged ? this.position().distanceTo(origin) : 7.0f;
                    if (teleportCount <= 1) magnitude *= 3;
                    double x = origin.x + Mth.cos(angle) * magnitude;
                    double y = origin.y + 3;
                    double z = origin.z + Mth.sin(angle) * magnitude;
                    if (this.teleport(x, y, z)) {
                        if (!wasRanged) {
                            teleportCount--;
                            if (teleportCount <= 0) {
                                int count = 8 + this.random.nextInt(5);
                                for (int j = 0; j < count; j++) {
                                    float fireballAngle = this.random.nextFloat() * Mth.TWO_PI;
                                    Vec3 offset = new Vec3(Math.cos(fireballAngle), 0.0, Math.sin(fireballAngle)).scale(0.1);
                                    RadiantEnergy bomb = new RadiantEnergy(this);
                                    bomb.setPos(currentPos.add(0.0, getBbHeight() / 2, 0.0));
                                    bomb.setDeltaMovement(offset.scale(this.random.nextFloat() + 1.0f));
                                    level().addFreshEntity(bomb);
                                }
                                teleportCount = 3 + random.nextInt(4);
                            }
                        }
                        return true;
                    }
                }
            }

            for (int i = 0; i < 64; i++) {
                double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
                double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
                double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
                if (this.teleport(d0, d1, d2)) return true;
            }
        }
        return false;
    }

    /**
     * Teleport the enderman
     */
    private boolean teleport(double pX, double pY, double pZ) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, pY, pZ);

        while(blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, pX, pY, pZ);
            if (event.isCanceled()) return false;
            Vec3 vec3 = this.position();
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
                if (!this.isSilent()) {
                    this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return flag2;
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ENDERMAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) return true;
        if (pEntity instanceof Player player) {
            if (player.isUsingItem() && player.getUseItem().is(Items.SHIELD)) {
                player.getCooldowns().addCooldown(Items.SHIELD, 50);
                player.stopUsingItem();
                this.level().broadcastEntityEvent(player, (byte) 30);
            }
        }
        return false;
    }

    @Override
    protected boolean canRide(Entity pVehicle) {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float pAmount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            boolean hitWithBottle = source.getDirectEntity() instanceof ThrownPotion;
            if (!source.is(DamageTypeTags.IS_PROJECTILE) && !hitWithBottle) {
                boolean wasHurt = super.hurt(source, pAmount);
                if (wasHurt && this.getTarget() != null && source.getEntity() == this.getTarget()) {
                    this.teleportInResponseToAttack(false);
                }

                return wasHurt;
            } else {
                boolean wasHurtByWater = hitWithBottle && this.hurtWithCleanWater(source, (ThrownPotion)source.getDirectEntity(), pAmount);

                if (this.teleportInResponseToAttack(true)) return true;

                return wasHurtByWater;
            }
        }
    }

    public boolean isDoingBullethell() {
        return isDoingBullethell;
    }

    private boolean hurtWithCleanWater(DamageSource pSource, ThrownPotion pPotion, float pAmount) {
        ItemStack itemstack = pPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemstack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
        boolean flag = potion == Potions.WATER && list.isEmpty();
        return flag && super.hurt(pSource, pAmount);
    }

    public boolean isCreepy() {
        return this.entityData.get(DATA_CREEPY);
    }

    public boolean hasBeenStaredAt() {
        return this.entityData.get(DATA_STARED_AT);
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.barInfo.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        barInfo.addPlayer(serverPlayer);
        EEPackets.sendToPlayer(serverPlayer, new SBossInfoPacket(SBossInfoPacket.Operation.ADD, barInfo.getId(), this));
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        barInfo.removePlayer(serverPlayer);
        EEPackets.sendToPlayer(serverPlayer, new SBossInfoPacket(SBossInfoPacket.Operation.REMOVE, barInfo.getId(), this));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity pEntity) {
        return EntityUtil.calculateMinimalAttackReach(this, pEntity);
    }

    @Override
    public EverendBossInfo<EnderLord> getBossInfo() {
        return new EverendBossInfo<>(this, EEMusic.ENDER_LORD);
    }

    private class EnderLordBulletHellGoal extends Goal {
        EnderLord owner;
        private int timer;
        private int cooldown = 400;
        private BulletHellType type;

        private Direction dir;
        BlockPos bulletHellOrigin;
        boolean alt;

        private static final int WARMUP_TIME = 30;
        private static final int COOLDOWN_TIME = 50;
        //private static final int TOTAL_RUNTIME = WARMUP_TIME + 300 + COOLDOWN_TIME;

        private EnderLordBulletHellGoal(EnderLord lord) {
            owner = lord;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (cooldown <= 0 && owner.getTarget() != null) {
                cooldown = owner.random.nextInt(100) + 100;
                return true;
            }
            cooldown--;
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return owner.getTarget() != null && timer <= getTotalRuntime();
        }

        @Override
        public void start() {
            isDoingBullethell = true;
            timer = 0;
            bulletHellOrigin = getTarget().blockPosition();

            switch (random.nextInt(3)) {
                case 0:
                    type = BulletHellType.DOORS_FROM_RANDOM_DIRECTIONS;
                    break;
                case 1:
                    type = BulletHellType.DOORS_FROM_ONE_DIRECTION;
                    dir = getRandomDirection();
                    break;
                case 2:
                    type = BulletHellType.WALLS;
                    dir = getRandomDirection();
                    alt = owner.random.nextBoolean();
            }
        }

        @Override
        public void stop() {
            isDoingBullethell = false;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (timer >= WARMUP_TIME && timer <= getTotalRuntime() - COOLDOWN_TIME && (timer - WARMUP_TIME) % type.interval == 0) {
                Entity target = getTarget();
                if (target == null) return; // for some reason this can happen???
                if (type == BulletHellType.DOORS_FROM_RANDOM_DIRECTIONS) dir = getRandomDirection();
                if (target.onGround()) {
                    if (type != BulletHellType.WALLS) {
                        bulletHellOrigin = target.blockPosition();
                    } else {
                        bulletHellOrigin = bulletHellOrigin.atY(target.blockPosition().getY());
                    }

                }
                switch (type) { // FIXME: you can jump over these sometimes. Make the origin move up & down like the others
                    case WALLS -> {
                        BlockPos pos = bulletHellOrigin.relative(dir.getOpposite(), 15);
                        BlockPos oppositePos = bulletHellOrigin.relative(dir, 15);
                        for (int i = 0; i < 25; i++) {
                            Portal portal;

                            portal = new Portal(owner, dir, 0.45);
                            portal.setPos(pos.relative(alt ? dir.getClockWise() : dir.getCounterClockWise(), i).getCenter());
                            level().addFreshEntity(portal);

                            if (i > 0) {
                                portal = new Portal(owner, dir.getOpposite(), 0.45);
                                portal.setPos(oppositePos.relative(alt ? dir.getCounterClockWise() : dir.getClockWise(), i).getCenter());
                                level().addFreshEntity(portal);
                            }
                        }
                    }
                    case DOORS_FROM_RANDOM_DIRECTIONS -> {
                        BlockPos pos = bulletHellOrigin.relative(dir.getOpposite(), 15);
                        boolean offset = random.nextBoolean();
                        if (!offset) {
                            Portal portal = new Portal(owner, dir, 0.5);
                            portal.setPos(pos.getCenter());
                            level().addFreshEntity(portal);
                        }
                        for (int i = 1; i < 15; i++) {
                            if (i % 2 == (offset ? 1 : 0)) {
                                Portal portal;

                                portal = new Portal(owner, dir, 0.5);
                                portal.setPos(pos.relative(dir.getClockWise(), i).getCenter());
                                level().addFreshEntity(portal);

                                portal = new Portal(owner, dir, 0.5);
                                portal.setPos(pos.relative(dir.getCounterClockWise(), i).getCenter());
                                level().addFreshEntity(portal);
                            }
                        }
                    }
                    case DOORS_FROM_ONE_DIRECTION -> {
                        BlockPos pos = bulletHellOrigin.relative(dir.getOpposite(), 15);
                        boolean offset = random.nextBoolean();
                        boolean vertical = random.nextBoolean();
                        if (vertical) {
                            for (int i = -14; i <= 14; i++) {
                                Portal portal = new Portal(owner, dir, 0.5);
                                portal.setHeight(0.4f);
                                double height = offset ? 1.1 : 0.0;
                                portal.setPos(pos.relative(dir.getClockWise(), i).getCenter().add(0.0, height, 0.0));
                                level().addFreshEntity(portal);
                            }
                        } else {
                            if (!offset) {
                                Portal portal = new Portal(owner, dir, 0.5);
                                portal.setPos(pos.getCenter());
                                level().addFreshEntity(portal);
                            }
                            for (int i = 1; i < 15; i++) {
                                if (i % 2 == (offset ? 1 : 0)) {
                                    Portal portal;

                                    portal = new Portal(owner, dir, 0.5);
                                    portal.setPos(pos.relative(dir.getClockWise(), i).getCenter());
                                    level().addFreshEntity(portal);

                                    portal = new Portal(owner, dir, 0.5);
                                    portal.setPos(pos.relative(dir.getCounterClockWise(), i).getCenter());
                                    level().addFreshEntity(portal);
                                }
                            }
                        }
                    }
                }
            }

            timer++;
        }

        private Direction getRandomDirection() {
            return Direction.from3DDataValue(2 + random.nextInt(4));
        }

        private int getTotalRuntime() {
            return WARMUP_TIME + type.duration + COOLDOWN_TIME;
        }

        private enum BulletHellType {
            DOORS_FROM_RANDOM_DIRECTIONS(40, 300),
            DOORS_FROM_ONE_DIRECTION(10, 180),
            WALLS(30, 100);

            private final int interval;
            private final int duration;

            BulletHellType(int time, int duration) {
                interval = time;
                this.duration = duration;
            }
        }
    }
}
