package net.purplemushroom.neverend.content.capability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.purplemushroom.neverend.registry.NECapabilities;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;
import ru.timeconqueror.timecore.common.capability.owner.serializer.CapabilityOwnerCodec;

import javax.annotation.Nullable;

public class NEPlayer //extends CoffeeCapabilityInstance<Entity> implements NEPlayerData
{

    /*private final ServerPlayer player;

    public NEPlayer(ServerPlayer player) {
        this.player = player;
    }

    @NotNull
    @Override
    public Capability<? extends CoffeeCapabilityInstance<Entity>> getCapability() {
        return NECapabilities.PLAYER;
    }

    @NotNull
    @Override
    public CapabilityOwnerCodec<Entity> getOwnerSerializer() {
        return CapabilityOwner.ENTITY.getSerializer();
    }

    @Override
    public void sendChangesToClients(@NotNull SimpleChannel channel, @NotNull Object data) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), data);
    }

    public void detectAndSendChanges() {
        detectAndSendChanges(player.level(), player);
    }

    public void sendAllData() {
        sendAllData(player.level(), player);
    }

    @Nullable
    public static NEPlayer from(@Nullable Player player) {
        if (player != null) {
            LazyOptional<NEPlayer> cap = player.getCapability(NECapabilities.PLAYER);
            if (cap.isPresent()) {
                return cap.orElseThrow(IllegalStateException::new);
            }
        }
        return null;
    }*/
}