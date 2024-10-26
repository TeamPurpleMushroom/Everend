package net.purplemushroom.neverend.event;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.screen.NeverendMenuScreen;

@Mod.EventBusSubscriber
public class MenuEventHandler {
    @SubscribeEvent
    public static void menuOverrideEvent(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof TitleScreen) {
            event.setNewScreen(new NeverendMenuScreen());
        }
    }
}
