package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.purplemushroom.neverend.content.entities.Rift;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;
import ru.timeconqueror.timecore.common.capability.property.serializer.IPropertySerializer;

public class RiftFishingData extends PropertyContainer {
    private final CoffeeProperty<Integer> rift = prop("rift", Integer.MIN_VALUE, RiftSerializer.INSTANCE).synced();
    private final CoffeeProperty<Float> fishingProgress = prop("rift_fishing_progress", Float.NaN, RiftProgressSerializer.INSTANCE).synced();

    public boolean isActive(Level level) {
        return !Float.isNaN(fishingProgress.get());
    }

    public Rift getRift(Level level) {
        Entity entity = level.getEntity(rift.get());
        if (entity instanceof Rift) return (Rift) entity;
        return null;
    }

    public void startFishingFromRift(Rift rift) {
        this.rift.set(rift.getId());
        this.fishingProgress.set(0.0f);
    }

    public void stopFishingFromRift() {
        rift.set(Integer.MIN_VALUE);
        fishingProgress.set(Float.NaN);
    }

    public boolean progressFishing(float amount) {
        float progress = fishingProgress.get() + amount;
        if (progress >= 1.0f) {
            stopFishingFromRift();
            return true;
        }
        fishingProgress.set(progress);
        return false;
    }

    public static class RiftSerializer implements IPropertySerializer<Integer> {
        public static final IPropertySerializer<Integer> INSTANCE = new RiftFishingData.RiftSerializer();

        @Override
        public void serialize(@NotNull String key, Integer integer, @NotNull CompoundTag compoundTag) {
            compoundTag.putInt(key, integer);
        }

        @Override
        public Integer deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
            return compoundTag.getInt(key);
        }
    }

    public static class RiftProgressSerializer implements IPropertySerializer<Float> {
        public static final IPropertySerializer<Float> INSTANCE = new RiftFishingData.RiftProgressSerializer();

        @Override
        public void serialize(@NotNull String key, Float value, @NotNull CompoundTag compoundTag) {
            compoundTag.putFloat(key, value);
        }

        @Override
        public Float deserialize(@NotNull String key, @NotNull CompoundTag compoundTag) {
            return compoundTag.getFloat(key);
        }
    }
}
