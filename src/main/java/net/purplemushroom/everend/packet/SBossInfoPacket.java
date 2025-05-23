package net.purplemushroom.everend.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.purplemushroom.everend.client.BossInfoHandler;
import net.purplemushroom.everend.content.entities.IEverendBoss;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.api.common.packet.IPacket;
import ru.timeconqueror.timecore.api.common.packet.ITimePacketHandler;

import java.io.IOException;
import java.util.UUID;

public class SBossInfoPacket implements IPacket {
    private Operation addOrRemove;
    private UUID barUUID;
    private int bossNum;

    private SBossInfoPacket(Operation operation, UUID barUUID, int bossNum) {
        this.addOrRemove = operation;
        this.barUUID = barUUID;
        this.bossNum = bossNum;
    }

    public SBossInfoPacket(Operation operation, UUID barUUID, Entity boss) {
        this(operation, barUUID, boss.getId());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(addOrRemove);
        buf.writeUUID(barUUID);
        buf.writeInt(bossNum);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        addOrRemove = buf.readEnum(Operation.class);
        barUUID = buf.readUUID();
        bossNum = buf.readInt();
    }

    @Override
    public void handleOnClient(NetworkEvent.Context ctx) {
        switch (addOrRemove) {
            case ADD:
                Entity entity = Minecraft.getInstance().level.getEntity(bossNum);
                if (entity instanceof IEverendBoss<?> boss) {
                    if (BossInfoHandler.info.put(barUUID, boss.getBossInfo()) != null) throw new IllegalStateException("Boss info already existed???");
                } else {
                    throw new IllegalStateException("Could not find boss to add info");
                }
                break;
            case REMOVE:
                BossInfoHandler.info.remove(barUUID);
        }
    }

    public enum Operation {
        ADD,
        REMOVE
    }
}
