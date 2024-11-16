package net.purplemushroom.neverend.capability.player.data;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.capability.player.data.serializers.BlockPositionSerializer;
import net.purplemushroom.neverend.capability.player.data.serializers.ResourceLocationSerializer;
import net.purplemushroom.neverend.capability.player.data.serializers.UUIDSerializer;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

import java.util.UUID;

//TODO: log different pos based on dimension, add fallback if area is destroyed
public class PlayerTracker extends PropertyContainer {
    private final CoffeeProperty<BlockPos> groundPos = prop("ground_pos", (BlockPos) null, BlockPositionSerializer.NULLABLE_INSTANCE).synced();
    private final CoffeeProperty<ResourceLocation> dimensionKey = prop("dimension", (ResourceLocation) null, ResourceLocationSerializer.NULLABLE_INSTANCE);
    private final CoffeeProperty<UUID> playerID = prop("player_id", (UUID) null, UUIDSerializer.NULLABLE_INSTANCE);
    private final CoffeeProperty<UUID> deathID = prop("death_id", (UUID) null, UUIDSerializer.NULLABLE_INSTANCE);

    public BlockPos getLastGroundPos() {
        return groundPos.get();
    }

    public ResourceLocation getDimension() {
        return dimensionKey.get();
    }

    public UUID getPlayerID() {
        return playerID.get();
    }

    public UUID getDeathID() {
        return deathID.get();
    }

    public void setLastGroundStats(BlockPos posOnGround, ResourceLocation dimension) {
        this.groundPos.set(posOnGround);
        this.dimensionKey.set(dimension);
    }

    public void setPlayerID(UUID uuid) {
        this.playerID.set(uuid);
    }

    public void setDeathID(UUID uuid) {
        this.deathID.set(uuid);
    }
}
