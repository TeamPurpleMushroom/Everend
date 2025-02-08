package net.purplemushroom.neverend.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.capability.player.data.serializers.BlockPositionSerializer;
import net.purplemushroom.neverend.capability.player.data.serializers.ResourceLocationSerializer;
import net.purplemushroom.neverend.util.datastructures.CircularQueue;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class PlayerTracker extends PropertyContainer {
    private final CircularQueue<BlockPos> posCache = new CircularQueue<>(50);
    private final CoffeeProperty<BlockPos> groundPos = prop("ground_pos", (BlockPos) null, BlockPositionSerializer.NULLABLE_INSTANCE).synced();
    private final CoffeeProperty<ResourceLocation> dimensionKey = prop("dimension", (ResourceLocation) null, ResourceLocationSerializer.NULLABLE_INSTANCE);

    public BlockPos getLastGroundPos() {
        return posCache.peek();
    }

    public BlockPos getAndRemoveLastGroundPos() {
        return posCache.dequeue();
    }

    public ResourceLocation getDimension() {
        return dimensionKey.get();
    }

    public void setLastGroundStats(BlockPos posOnGround, ResourceLocation dimension) {
        if (dimension != dimensionKey.get()) {
            this.dimensionKey.set(dimension);
            this.posCache.clear();
        }
        this.posCache.enqueue(posOnGround);
        //this.groundPos.set(posOnGround);
        //this.dimensionKey.set(dimension);
    }

    public boolean isCacheEmpty() {
        return posCache.isEmpty();
    }
}
