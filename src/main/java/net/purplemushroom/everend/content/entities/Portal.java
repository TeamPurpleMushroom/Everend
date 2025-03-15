package net.purplemushroom.everend.content.entities;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.registry.EEEffects;
import net.purplemushroom.everend.registry.EEEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Portal extends Entity implements TraceableEntity {
    private UUID creatorUUID;
    private EnderLord creator;
    private double speed;
    private static final EntityDataAccessor<Direction> DATA_DIRECTION_ID = SynchedEntityData.defineId(Portal.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = SynchedEntityData.defineId(Portal.class, EntityDataSerializers.FLOAT);

    public Portal(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Portal(EnderLord creator, Direction dir, double speed) {
        this(EEEntities.PORTAL_TYPE.get(), creator.level());
        setOwner(creator);
        setDirection(dir);
        this.speed = speed;
    }

    @Override
    public void tick() {
        if (!level().isClientSide() && tickCount > 10) {
            Vec3i vec = getDirection().getNormal();
            setDeltaMovement(new Vec3(vec.getX(), vec.getY(), vec.getZ()).scale(speed));
            if (tickCount >= 100) kill();

            for (Entity entity : level().getEntities(this, this.getBoundingBox())) {
                if (entity != getOwner() && entity instanceof LivingEntity living && living.hurt(living.damageSources().fellOutOfWorld(), 3.0f)) { // TODO: custom damage type!
                    MobEffectInstance prevEffect = living.getEffect(EEEffects.RIFT_TORN.get());
                    int amplifier = prevEffect != null ? prevEffect.getAmplifier() + 1 : 0;
                    living.addEffect(new MobEffectInstance(EEEffects.RIFT_TORN.get(), 5 * 60 * 20, amplifier));
                }
            }
        }
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(0.8f, getHeight());
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_DIRECTION_ID, Direction.WEST);
        this.entityData.define(DATA_HEIGHT_ID, getBbHeight());
    }

    protected void setHeight(float height) {
        this.entityData.set(DATA_HEIGHT_ID, height);
    }

    private float getHeight() {
        return this.entityData.get(DATA_HEIGHT_ID);
    }

    private void setDirection(Direction dir) {
        this.entityData.set(DATA_DIRECTION_ID, dir);
    }

    public Direction getDirection() {
        return this.entityData.get(DATA_DIRECTION_ID);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_HEIGHT_ID.equals(pKey)) refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        creatorUUID = pCompound.getUUID("Creator");
        speed = pCompound.getDouble("Speed");
        setDirection(Direction.from3DDataValue(pCompound.getInt("Direction")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putUUID("Creator", creatorUUID);
        pCompound.putDouble("Speed", speed);
        pCompound.putInt("Direction", getDirection().get3DDataValue());
    }

    public void setOwner(EnderLord owner) {
        this.creator = owner;
        this.creatorUUID = creator.getUUID();
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (creator == null && level() instanceof ServerLevel level) {
            if (level.getEntity(creatorUUID) instanceof EnderLord lord) {
                creator = lord;
            }
        }
        return creator;
    }
}
