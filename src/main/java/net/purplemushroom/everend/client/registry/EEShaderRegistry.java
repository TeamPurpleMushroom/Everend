package net.purplemushroom.everend.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Everend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EEShaderRegistry {
    public static ShaderInstance shaderVoidStars;

    public static ShaderInstance shaderMenu;

    public static ShaderInstance shaderEndSky;

    public static ShaderInstance shaderBossBar;

    public static ShaderInstance getShaderVoidStars() {
        return shaderVoidStars;
    }

    public static ShaderInstance getShaderMenu() {
        return shaderMenu;
    }

    public static ShaderInstance getShaderEndSky() {return shaderEndSky;}

    public static ShaderInstance getShaderBossBar() {return shaderBossBar;}

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Everend.MODID, "rendertype_void_stars"), DefaultVertexFormat.POSITION),
                shaderInstance -> shaderVoidStars = shaderInstance);

        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Everend.MODID, "rendertype_menu"), DefaultVertexFormat.POSITION),
                shaderInstance -> shaderMenu = shaderInstance);

        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Everend.MODID, "end_sky"), DefaultVertexFormat.POSITION_TEX),
                shaderInstance -> shaderEndSky = shaderInstance);

        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Everend.MODID, "rendertype_boss_bar"), DefaultVertexFormat.POSITION),
                shaderInstance -> shaderBossBar = shaderInstance);
    }
}
