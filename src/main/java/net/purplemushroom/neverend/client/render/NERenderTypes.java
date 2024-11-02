package net.purplemushroom.neverend.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

public class NERenderTypes {
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

    public static RenderType getRiftPortalRenderType() {
        return RIFT_PORTAL_RENDER_TYPE;
    }
}
