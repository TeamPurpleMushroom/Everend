package net.purplemushroom.neverend.registry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import ru.timeconqueror.timecore.api.CapabilityManagerAPI;
import ru.timeconqueror.timecore.api.registry.CapabilityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NECapabilities {
    @AutoRegistrable
    private static final CapabilityRegister REGISTER = new CapabilityRegister(Neverend.MODID);

    public static final Capability<NEPlayer> PLAYER = REGISTER.register(NEPlayer.class);

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> CapabilityManagerAPI.registerStaticCoffeeAttacher(CapabilityOwner.ENTITY, PLAYER, entity -> entity instanceof Player, entity -> new NEPlayer((Player) entity))); // TODO: maybe there should be 2 capabilities, one for server-only stuff and one for synced stuff?
    }
}