package net.purplemushroom.everend.client.registry;

import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.render.entity.BaseRiftRenderer;
import net.purplemushroom.everend.client.render.entity.EnderLordRenderer;
import net.purplemushroom.everend.client.render.entity.PortalRenderer;
import net.purplemushroom.everend.client.render.entity.RadiantEnergyRenderer;
import net.purplemushroom.everend.registry.EEEntities;

@Mod.EventBusSubscriber(modid = Everend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EEEntityRenderRegistry {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EEEntities.FISHING_RIFT_TYPE.get(), manager -> new BaseRiftRenderer(manager, BaseRiftRenderer.RiftType.FISHING)
                .fullbright(true)
                .blockLightLevel(2)
                .scale(5.5F));

        event.registerEntityRenderer(EEEntities.VOID_RIFT_TYPE.get(), manager -> new BaseRiftRenderer(manager, BaseRiftRenderer.RiftType.VOID)
                .fullbright(true)
                .blockLightLevel(2)
                .scale(5.5F));

        event.registerEntityRenderer(EEEntities.ENDER_LORD_TYPE.get(), EnderLordRenderer::new);

        event.registerEntityRenderer(EEEntities.RADIANT_ENERGY_TYPE.get(), RadiantEnergyRenderer::new);

        event.registerEntityRenderer(EEEntities.PORTAL_TYPE.get(), PortalRenderer::new);
    }
}