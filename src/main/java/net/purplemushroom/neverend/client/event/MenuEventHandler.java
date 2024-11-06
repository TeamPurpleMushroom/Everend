package net.purplemushroom.neverend.client.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.client.render.screen.menu.NeverendMenuScreen;

@Mod.EventBusSubscriber
public class MenuEventHandler {
    @SubscribeEvent
    public static void menuOverrideEvent(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        if (screen instanceof TitleScreen && !(screen instanceof NeverendMenuScreen)) {
            event.setNewScreen(new NeverendMenuScreen());
        }
    }
}
