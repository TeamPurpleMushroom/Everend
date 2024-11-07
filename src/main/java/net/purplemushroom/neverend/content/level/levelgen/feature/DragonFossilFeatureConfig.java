package net.purplemushroom.neverend.content.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.List;

public class DragonFossilFeatureConfig implements FeatureConfiguration {
    public static final Codec<DragonFossilFeatureConfig> CODEC = RecordCodecBuilder.create((
            config) -> config.group(ResourceLocation.CODEC.listOf().fieldOf("fossil_structures").forGetter((
            config1) -> config1.fossilStructures), ResourceLocation.CODEC.listOf().fieldOf("overlay_structures").forGetter((
            config2) -> config2.overlayStructures), StructureProcessorType.LIST_CODEC.fieldOf("fossil_processors").forGetter((
            config3) -> config3.fossilProcessors), StructureProcessorType.LIST_CODEC.fieldOf("overlay_processors").forGetter((
            config4) -> config4.overlayProcessors), Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter((
            config5) -> config5.maxEmptyCornersAllowed)).apply(config, DragonFossilFeatureConfig::new));
    public final List<ResourceLocation> fossilStructures;
    public final List<ResourceLocation> overlayStructures;
    public final Holder<StructureProcessorList> fossilProcessors;
    public final Holder<StructureProcessorList> overlayProcessors;
    public final int maxEmptyCornersAllowed;

    public DragonFossilFeatureConfig(List<ResourceLocation> fossilStructures, List<ResourceLocation> overlayStructures, Holder<StructureProcessorList> fossilProcessors, Holder<StructureProcessorList> overlayProcessors, int maxEmptyCorners) {
        if (fossilStructures.isEmpty()) {
            throw new IllegalArgumentException("Dragon Fossil structure lists need at least one entry");
        } else if (fossilStructures.size() != overlayStructures.size()) {
            throw new IllegalArgumentException("Dragon Fossil structure lists must be equal lengths");
        } else {
            this.fossilStructures = fossilStructures;
            this.overlayStructures = overlayStructures;
            this.fossilProcessors = fossilProcessors;
            this.overlayProcessors = overlayProcessors;
            this.maxEmptyCornersAllowed = maxEmptyCorners;
        }
    }
}