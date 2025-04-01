package net.purplemushroom.everend.registry;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.packet.SBossInfoPacket;
import ru.timeconqueror.timecore.api.registry.PacketRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

public class EEPackets {
    @AutoRegistrable
    private static final PacketRegister REGISTER = new PacketRegister(Everend.MODID);

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = REGISTER.createChannel("main", () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals)
            .regPacket(SBossInfoPacket.class)
            .asChannel();

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        EEPackets.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

}
