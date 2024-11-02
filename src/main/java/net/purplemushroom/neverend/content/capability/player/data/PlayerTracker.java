package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.content.capability.player.data.serializers.BlockPositionSerializer;
import net.purplemushroom.neverend.content.capability.player.data.serializers.ResourceLocationSerializer;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

//TODO: log different pos based on dimension, add fallback if area is destroyed
public class PlayerTracker extends PropertyContainer {

    private final CoffeeProperty<BlockPos> groundPos = prop("ground_pos", (BlockPos) null, BlockPositionSerializer.INSTANCE).synced();
    private final CoffeeProperty<ResourceLocation> dimensionKey = prop("dimension", (ResourceLocation) null, ResourceLocationSerializer.INSTANCE);

    public BlockPos getLastGroundPos() {
        return groundPos.get();
    }

    public ResourceLocation getDimension() {
        return dimensionKey.get();
    }

    public void setLastGroundStats(BlockPos posOnGround, ResourceLocation dimension) {
        this.groundPos.set(posOnGround);
        this.dimensionKey.set(dimension);
    }
}
