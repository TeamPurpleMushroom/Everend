package net.purplemushroom.neverend.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.NERenderTypes;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Neverend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEShaderRegistry {

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, new ResourceLocation(Neverend.MODID, "rendertype_void_stars"), DefaultVertexFormat.POSITION),
                shaderInstance -> NERenderTypes.Shaders.shaderVoidStars = shaderInstance);
    }
}
