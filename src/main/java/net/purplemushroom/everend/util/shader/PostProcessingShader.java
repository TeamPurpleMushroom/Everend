package net.purplemushroom.everend.util.shader;

import net.minecraft.resources.ResourceLocation;

public class PostProcessingShader {
    public final ResourceLocation location;
    public final EnumShaderLayer layer;
    public final boolean removeOnWorldClose;

    public PostProcessingShader(ResourceLocation location, EnumShaderLayer layer, boolean removeOnWorldClose) {
        this.location = location;
        this.layer = layer;
        this.removeOnWorldClose = removeOnWorldClose;
    }
}
