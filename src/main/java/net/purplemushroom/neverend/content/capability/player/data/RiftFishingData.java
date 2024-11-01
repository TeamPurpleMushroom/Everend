package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.purplemushroom.neverend.content.entities.Rift;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class RiftFishingData extends PropertyContainer {
    private final CoffeeProperty<Integer> rift = prop("rift", Integer.MIN_VALUE);
    private final CoffeeProperty<Float> fishingProgress = prop("rift_fishing_progress", Float.NaN);

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
}
