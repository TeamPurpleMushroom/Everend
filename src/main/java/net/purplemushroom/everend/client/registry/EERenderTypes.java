package net.purplemushroom.everend.client.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.purplemushroom.everend.Everend;

public class EERenderTypes {

    public static final RenderType RIFT_PORTAL_RENDER_TYPE = RenderType.create(
            "end_space",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.TRIANGLE_STRIP,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEndPortalShader))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                            .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                            .build())
                    .createCompositeState(false));

    public static final RenderType VOID_STARS_TRI_RENDER_TYPE = RenderType.create(
            "void_stars",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.TRIANGLE_STRIP,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(EEShaderRegistry::getShaderVoidStars))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                            .add(Everend.tl("entity/void_stars.png").fullLocation(), false, false)
                            .build())
                    .createCompositeState(false));

    public static final RenderType VOID_STARS_QUADS_RENDER_TYPE = RenderType.create(
            "void_stars",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(EEShaderRegistry::getShaderVoidStars))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                            .add(Everend.tl("entity/void_stars.png").fullLocation(), false, false)
                            .build())
                    .createCompositeState(false));

    public static final RenderType MENU_RENDER_TYPE = RenderType.create(
            "main_menu",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(EEShaderRegistry::getShaderMenu))
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(Everend.tl("gui/nebula.png").fullLocation(), false, false)
                            .add(Everend.tl("entity/void_stars.png").fullLocation(), false, false)
                            //.add(Neverend.tl("gui/menu_island.png").fullLocation(), false, false)
                            .build())
                    .createCompositeState(false));

    public static RenderType getRiftPortalRenderType() {
        return RIFT_PORTAL_RENDER_TYPE;
    }

    public static RenderType getVoidStarsTriRenderType() {
        return VOID_STARS_TRI_RENDER_TYPE;
    }

    public static RenderType getVoidStarsQuadsRenderType() {
        return VOID_STARS_QUADS_RENDER_TYPE;
    }

    public static RenderType getMenuRenderType() {
        return MENU_RENDER_TYPE;
    }

    public static class Shaders {

    }
}
