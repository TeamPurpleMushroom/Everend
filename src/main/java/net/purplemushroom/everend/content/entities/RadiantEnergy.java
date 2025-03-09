package net.purplemushroom.everend.content.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.purplemushroom.everend.registry.EEEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RadiantEnergy extends Entity implements TraceableEntity {
    private UUID creatorUUID;
    private EnderLord creator;
    private static final EntityDataAccessor<Integer> DATA_TIME_ID = SynchedEntityData.defineId(RadiantEnergy.class, EntityDataSerializers.INT);

    public RadiantEnergy(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public RadiantEnergy(EnderLord creator) {
        this(EEEntities.RADIANT_ENERGY_TYPE.get(), creator.level());
        setOwner(creator);
    }

    @Override
    public void tick() {
        this.setPos(position().add(this.getDeltaMovement()));
        if (!level().isClientSide()) {
            setTime(getTime() + 1);
            if (getTime() >= 80) {
                this.kill();
                level().explode(getOwner(), getX(), getY(), getZ(), 3.0f, false, Level.ExplosionInteraction.NONE);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_TIME_ID, 0);
    }

    public void setTime(int time) {
        this.entityData.set(DATA_TIME_ID, time);
    }

    public int getTime() {
        return this.entityData.get(DATA_TIME_ID);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        creatorUUID = pCompound.getUUID("Creator");
        setTime(pCompound.getInt("Time"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putUUID("Creator", creatorUUID);
        pCompound.putInt("Time", getTime());
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
