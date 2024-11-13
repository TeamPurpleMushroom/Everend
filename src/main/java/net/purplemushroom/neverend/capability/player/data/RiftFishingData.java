package net.purplemushroom.neverend.capability.player.data;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.purplemushroom.neverend.content.entities.FishingRift;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class RiftFishingData extends PropertyContainer {
    private final CoffeeProperty<Integer> fishingRift = nullableProp("fishing_rift", (Integer) null).synced();
    private final CoffeeProperty<Float> fishingProgress = nullableProp("rift_fishing_progress", (Float) null).synced();

    public boolean isActive() {
        return fishingProgress.get() != null;
    }

    public FishingRift getFishingRift(Level level) {
        Entity entity = level.getEntity(fishingRift.get());
        if (entity instanceof FishingRift) return (FishingRift) entity;
        return null;
    }

    public void startFishingFromRift(FishingRift fishingRift) {
        this.fishingRift.set(fishingRift.getId());
        this.fishingProgress.set(0.0f);
    }

    public void stopFishingFromRift() {
        fishingRift.set(null);
        fishingProgress.set(null);
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

    public float getProgress() {
        Float progress = fishingProgress.get();
        if (progress == null) return 0.0f;
        return fishingProgress.get();
    }
}
