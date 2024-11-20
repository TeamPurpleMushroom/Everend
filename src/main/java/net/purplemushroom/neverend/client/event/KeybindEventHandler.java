package net.purplemushroom.neverend.client.event;

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

import static net.purplemushroom.neverend.client.isometric.IsometricCameraHandler.handleIsometricCameraKeys;

@Mod.EventBusSubscriber(modid = Neverend.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeybindEventHandler {
    public static KeyMapping keyIsometricView;
    public static KeyMapping keyLockPerspective;
    public static KeyMapping keyMoveCameraUp;
    public static KeyMapping keyMoveCameraDown;
    public static KeyMapping keyMoveCameraLeft;
    public static KeyMapping keyMoveCameraRight;
    public static KeyMapping keyRotateCameraClockwise;
    public static KeyMapping keyRotateCameraCounterClockwise;
    public static KeyMapping keyResetRotation;
    public static KeyMapping keyResetCameraPosition;
    public static KeyMapping keyResetAll;
    public static KeyMapping keyCycleSnapAngle;

    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        keyIsometricView = new KeyMapping("key.neverend.toggle_iso_cam.desc", GLFW.GLFW_KEY_EQUAL, I18n.get("key.neverend.category"));
        keyLockPerspective = new KeyMapping("key.neverend.lock_iso.desc", GLFW.GLFW_KEY_DELETE, I18n.get("key.neverend.category"));
        keyMoveCameraUp = new KeyMapping("key.neverend.move_cam_up.desc", GLFW.GLFW_KEY_UP, I18n.get("key.neverend.category"));
        keyMoveCameraDown = new KeyMapping("key.neverend.move_cam_down.desc", GLFW.GLFW_KEY_DOWN, I18n.get("key.neverend.category"));
        keyMoveCameraLeft = new KeyMapping("key.neverend.move_cam_left.desc", GLFW.GLFW_KEY_LEFT, I18n.get("key.neverend.category"));
        keyMoveCameraRight = new KeyMapping("key.neverend.move_cam_right.desc", GLFW.GLFW_KEY_RIGHT, I18n.get("key.neverend.category"));
        keyRotateCameraClockwise = new KeyMapping("key.neverend.rotate_cam_clockwise.desc", GLFW.GLFW_KEY_RIGHT_BRACKET, I18n.get("key.neverend.category"));
        keyRotateCameraCounterClockwise = new KeyMapping("key.neverend.rotate_cam_anticlockwise.desc", GLFW.GLFW_KEY_LEFT_BRACKET, I18n.get("key.neverend.category"));
        keyResetRotation = new KeyMapping("key.neverend.reset_cam_rotation.desc", GLFW.GLFW_KEY_END, I18n.get("key.neverend.category"));
        keyResetCameraPosition = new KeyMapping("key.neverend.reset_cam_pos.desc", GLFW.GLFW_KEY_PAGE_DOWN, I18n.get("key.neverend.category"));
        keyResetAll = new KeyMapping("key.neverend.reset_all.desc", GLFW.GLFW_KEY_BACKSLASH, I18n.get("key.neverend.category"));
        keyCycleSnapAngle = new KeyMapping("key.neverend.cycle_iso_snap_angle.desc", GLFW.GLFW_KEY_MINUS, I18n.get("key.neverend.category"));

        event.register(keyIsometricView);
        event.register(keyLockPerspective);
        event.register(keyMoveCameraUp);
        event.register(keyMoveCameraDown);
        event.register(keyMoveCameraLeft);
        event.register(keyMoveCameraRight);
        event.register(keyRotateCameraClockwise);
        event.register(keyRotateCameraCounterClockwise);
        event.register(keyResetRotation);
        event.register(keyResetAll);
        event.register(keyCycleSnapAngle);
    }
}