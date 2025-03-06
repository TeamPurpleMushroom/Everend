package net.purplemushroom.everend.capability.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.purplemushroom.everend.capability.player.data.PlayerTracker;
import net.purplemushroom.everend.capability.player.data.RiftFishingData;
import net.purplemushroom.everend.registry.EECapabilities;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.CoffeeCapabilityInstance;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;
import ru.timeconqueror.timecore.common.capability.owner.serializer.CapabilityOwnerCodec;

import javax.annotation.Nullable;

public class EEPlayer extends CoffeeCapabilityInstance<Entity> implements EEPlayerData {
    public final PlayerTracker playerTracker = container("player_tracker", new PlayerTracker());
    public final RiftFishingData riftFishingData = container("rift_fishing_data", new RiftFishingData());

    private final Player player;

    public EEPlayer(Player player) {
        this.player = player;
    }

    @NotNull
    @Override
    public Capability<? extends CoffeeCapabilityInstance<Entity>> getCapability() {
        return EECapabilities.PLAYER;
    }

    @NotNull
    @Override
    public CapabilityOwnerCodec<Entity> getOwnerSerializer() {
        return CapabilityOwner.ENTITY.getSerializer();
    }

    @Override
    public void sendChangesToClient(@NotNull SimpleChannel channel, @NotNull Object data) {
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), data);
    }

    public void detectAndSendChanges() {
        detectAndSendChanges(player.level(), player);
    }

    public void sendAllData() {
        sendAllData(player.level(), player);
    }

    @Nullable
    public static EEPlayer from(@Nullable Player player) {
        if (player != null) {
            LazyOptional<EEPlayer> cap = player.getCapability(EECapabilities.PLAYER);
            if (cap.isPresent()) {
                return cap.orElseThrow(IllegalStateException::new);
            }
        }
        return null;
    }

    public static LazyOptional<EEPlayer> of(Player player) {
        return player.getCapability(EECapabilities.PLAYER);
    }
}