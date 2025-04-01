package net.purplemushroom.everend.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.purplemushroom.everend.capability.player.EEPlayer;
import net.purplemushroom.everend.content.entities.FishingRift;
import ru.timeconqueror.timecore.api.common.packet.IPacket;

import java.util.UUID;

public class SRiftFishingPacket implements IPacket {
    private float progress;
    private int entity;

    private SRiftFishingPacket(int rift, float progress) {
        entity = rift;
        this.progress = progress;
    }

    public SRiftFishingPacket(FishingRift rift, Float progress) {
        this(rift != null ? rift.getId() : -1, progress != null ? progress : -1.0F);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(progress);
        buf.writeInt(entity);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        progress = buf.readFloat();
        entity = buf.readInt();
    }

    @Override
    public void handleOnClient(NetworkEvent.Context ctx) {
        EEPlayer playerCap = EEPlayer.from(Minecraft.getInstance().player);
        if (playerCap != null) {
            playerCap.riftFishingData.recieveChanges(entity, progress);
        }
    }
}
