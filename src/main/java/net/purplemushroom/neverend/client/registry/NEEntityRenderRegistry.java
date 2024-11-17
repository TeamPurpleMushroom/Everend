package net.purplemushroom.neverend.client.registry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.entity.BaseRiftRenderer;
import net.purplemushroom.neverend.registry.NEEntities;

@Mod.EventBusSubscriber(modid = Neverend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEEntityRenderRegistry {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NEEntities.FISHING_RIFT_TYPE.get(), manager -> new BaseRiftRenderer(manager, BaseRiftRenderer.RiftType.FISHING)
                .fullbright(true)
                .blockLightLevel(2)
                .scale(5.5F));

        event.registerEntityRenderer(NEEntities.VOID_RIFT_TYPE.get(), manager -> new BaseRiftRenderer(manager, BaseRiftRenderer.RiftType.VOID)
                .fullbright(true)
                .blockLightLevel(2)
                .scale(5.5F));
    }
}