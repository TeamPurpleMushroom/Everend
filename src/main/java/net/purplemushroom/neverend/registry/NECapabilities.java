package net.purplemushroom.neverend.registry;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.NEPlayer;
import ru.timeconqueror.timecore.api.registry.CapabilityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NECapabilities {
    @AutoRegistrable
    private static final CapabilityRegister REGISTER = new CapabilityRegister(Neverend.MODID);

    public static final Capability<NEPlayer> PLAYER = REGISTER.register(NEPlayer.class);

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        //event.enqueueWork(() -> CapabilityManagerAPI.registerStaticCoffeeAttacher(CapabilityOwner.ENTITY, PLAYER, entity -> entity instanceof ServerPlayer, entity -> new NEPlayer((ServerPlayer) entity)));
    }
}