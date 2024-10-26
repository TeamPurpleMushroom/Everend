package net.purplemushroom.neverend.content.capability.player.data;

import net.minecraft.core.BlockPos;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class PlayerFallTracker extends PropertyContainer {

    private final CoffeeProperty<BlockPos> groundPos = prop("ground_pos", (BlockPos) null, BlockPosHandler.Serializer.INSTANCE).synced();

    public BlockPos getLastGroundPos() {
        return groundPos.get();
    }

    public void setLastGroundPos(BlockPos posInAir) {
        this.groundPos.set(posInAir);
    }
}
