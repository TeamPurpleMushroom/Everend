package net.purplemushroom.everend.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.BossInfoHandler;
import net.purplemushroom.everend.util.EverendBossInfo;

@Mod.EventBusSubscriber(modid= Everend.MODID, value = Dist.CLIENT)
public class EEBossOverlayHandler {

    @SubscribeEvent
    public static void overrideBossBarGUI(CustomizeGuiOverlayEvent.BossEventProgress event) {
        EverendBossInfo<?> bossInfo = BossInfoHandler.info.get(event.getBossEvent().getId());
        if (bossInfo != null) {
            bossInfo.renderBar(event.getGuiGraphics(), event.getX(), event.getY());
            event.setCanceled(true);
        }
    }
}
