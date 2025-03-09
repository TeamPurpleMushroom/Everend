package net.purplemushroom.everend.content.entities;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.registry.EEEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Portal extends Entity implements TraceableEntity {
    private UUID creatorUUID;
    private EnderLord creator;
    private static final EntityDataAccessor<Direction> DATA_DIRECTION_ID = SynchedEntityData.defineId(Portal.class, EntityDataSerializers.DIRECTION);

    public Portal(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Portal(EnderLord creator, Direction dir) {
        this(EEEntities.PORTAL_TYPE.get(), creator.level());
        setOwner(creator);
        setDirection(dir);
    }

    @Override
    public void tick() {
        if (!level().isClientSide()) {
            Vec3i vec = getDirection().getNormal();
            setDeltaMovement(new Vec3(vec.getX(), vec.getY(), vec.getZ()).scale(0.05));
        }
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_DIRECTION_ID, Direction.WEST);
    }

    private void setDirection(Direction dir) {
        this.entityData.set(DATA_DIRECTION_ID, dir);
    }

    public Direction getDirection() {
        return this.entityData.get(DATA_DIRECTION_ID);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        creatorUUID = pCompound.getUUID("Creator");
        setDirection(Direction.from3DDataValue(pCompound.getInt("Direction")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putUUID("Creator", creatorUUID);
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
