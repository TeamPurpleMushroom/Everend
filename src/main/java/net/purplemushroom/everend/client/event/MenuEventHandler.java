package net.purplemushroom.everend.client.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.client.render.screen.menu.EverendMenuScreen;

@Mod.EventBusSubscriber
public class MenuEventHandler {
    @SubscribeEvent
    public static void menuOverrideEvent(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        if (screen instanceof TitleScreen && !(screen instanceof EverendMenuScreen)) {
            event.setNewScreen(new EverendMenuScreen());
        }
    }
}
