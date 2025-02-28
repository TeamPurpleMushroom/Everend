package net.purplemushroom.neverend.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;

import javax.annotation.Nullable;
import java.io.IOException;

@Mod.EventBusSubscriber(modid = Neverend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEShaderRegistry {
    public static ShaderInstance shaderVoidStars;

    public static ShaderInstance shaderMenu;

    public static ShaderInstance shaderEndSky;

    public static ShaderInstance getShaderVoidStars() {
        return shaderVoidStars;
    }

    public static ShaderInstance getShaderMenu() {
        return shaderMenu;
    }

    public static ShaderInstance getShaderEndSky() {return shaderEndSky;}

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Neverend.MODID, "rendertype_void_stars"), DefaultVertexFormat.POSITION),
                shaderInstance -> shaderVoidStars = shaderInstance);

        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Neverend.MODID, "rendertype_menu"), DefaultVertexFormat.POSITION),
                shaderInstance -> shaderMenu = shaderInstance);

        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Neverend.MODID, "end_sky"), DefaultVertexFormat.POSITION_TEX),
                shaderInstance -> shaderEndSky = shaderInstance);
    }
}
