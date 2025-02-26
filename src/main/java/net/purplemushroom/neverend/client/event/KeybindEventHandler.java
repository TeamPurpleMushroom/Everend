/*package net.purplemushroom.neverend.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Neverend.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeybindEventHandler {
    //public static KeyMapping keyIsometricView;

    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        //keyIsometricView = new KeyMapping("key.neverend.toggle_iso_cam.desc", GLFW.GLFW_KEY_EQUAL, I18n.get("key.neverend.category"));

        //event.register(keyIsometricView);
    }
}*/