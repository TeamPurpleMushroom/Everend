/*package net.purplemushroom.neverend.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import org.lwjgl.glfw.GLFW;

public class KeyInputEventHandler {

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        InputConstants.Key key = InputConstants.getKey(event.getKey(), event.getScanCode());
        if (minecraft.screen == null) {
            assert minecraft.player != null;
            int action = event.getAction();

            if (action == GLFW.GLFW_PRESS) {
                //Key actions here
            }
        }
    }
}*/
