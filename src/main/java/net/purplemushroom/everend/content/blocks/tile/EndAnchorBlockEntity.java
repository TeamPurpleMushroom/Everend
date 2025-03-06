package net.purplemushroom.everend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.everend.registry.EEBlockEntities;

import java.util.Objects;

public class EndAnchorBlockEntity extends BlockEntity {
    public EndAnchorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EEBlockEntities.END_ANCHOR, pPos, pBlockState);
    }

    public boolean shouldRenderFace(Direction pFace) {
        return Block.shouldRenderFace(this.getBlockState(), Objects.requireNonNull(this.getLevel()), this.getBlockPos(), pFace, this.getBlockPos().relative(pFace));
    }
}
