package net.purplemushroom.everend.content.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.Objects;

public class DragonFossilFeature extends Feature<DragonFossilFeatureConfig> {
    public DragonFossilFeature(Codec<DragonFossilFeatureConfig> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DragonFossilFeatureConfig> pContext) {
        RandomSource random = pContext.random();
        WorldGenLevel level = pContext.level();
        BlockPos blockPos = pContext.origin();
        Rotation rotation = Rotation.getRandom(random);
        DragonFossilFeatureConfig dragonFossilFeatureConfig = pContext.config();
        int size = random.nextInt(dragonFossilFeatureConfig.fossilStructures.size());
        StructureTemplateManager structureManager = level.getLevel().getServer().getStructureManager();
        StructureTemplate fossilTemplate = structureManager.getOrCreate(dragonFossilFeatureConfig.fossilStructures.get(size));
        StructureTemplate overlayTemplate = structureManager.getOrCreate(dragonFossilFeatureConfig.overlayStructures.get(size));
        ChunkPos chunkPos = new ChunkPos(blockPos);
        BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX() - 16, level.getMinBuildHeight(), chunkPos.getMinBlockZ() - 16, chunkPos.getMaxBlockX() + 16, level.getMaxBuildHeight(), chunkPos.getMaxBlockZ() + 16);
        StructurePlaceSettings structurePlaceSettings = (new StructurePlaceSettings()).setRotation(rotation).setBoundingBox(boundingBox).setRandom(random);
        Vec3i fossilTemplateSize = fossilTemplate.getSize(rotation);
        BlockPos offset = blockPos.offset(-fossilTemplateSize.getX() / 2, 0, -fossilTemplateSize.getZ() / 2);
        int blockPosY = blockPos.getY();

        int i;
        for (i = 0; i < fossilTemplateSize.getX(); ++i) {
            for (int i1 = 0; i1 < fossilTemplateSize.getZ(); ++i1) {
                blockPosY = Math.min(blockPosY, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, offset.getX() + i, offset.getZ() + i1));
            }
        }

        i = Math.max(blockPosY - 15 - random.nextInt(10), level.getMinBuildHeight() + 10);
        BlockPos zeroPositionWithTransform = fossilTemplate.getZeroPositionWithTransform(offset.atY(i), Mirror.NONE, rotation);
        if (countEmptyCorners(level, fossilTemplate.getBoundingBox(structurePlaceSettings, zeroPositionWithTransform)) > dragonFossilFeatureConfig.maxEmptyCornersAllowed) {
            return false;
        } else {
            structurePlaceSettings.clearProcessors();
            List<StructureProcessor> structureProcessorList = dragonFossilFeatureConfig.fossilProcessors.value().list();
            Objects.requireNonNull(structurePlaceSettings);
            structureProcessorList.forEach(structurePlaceSettings::addProcessor);
            fossilTemplate.placeInWorld(level, zeroPositionWithTransform, zeroPositionWithTransform, structurePlaceSettings, random, 4);
            structurePlaceSettings.clearProcessors();
            structureProcessorList = dragonFossilFeatureConfig.overlayProcessors.value().list();
            Objects.requireNonNull(structurePlaceSettings);
            structureProcessorList.forEach(structurePlaceSettings::addProcessor);
            overlayTemplate.placeInWorld(level, zeroPositionWithTransform, zeroPositionWithTransform, structurePlaceSettings, random, 4);
            return true;
        }
    }

    private static int countEmptyCorners(WorldGenLevel pLevel, BoundingBox pBoundingBox) {
        MutableInt mutableInt = new MutableInt(0);
        pBoundingBox.forAllCorners((blockPos) -> {
            BlockState blockState = pLevel.getBlockState(blockPos);
            if (blockState.isAir() || blockState.is(Blocks.LAVA) || blockState.is(Blocks.WATER)) {
                mutableInt.add(1);
            }

        });
        return mutableInt.getValue();
    }
}
