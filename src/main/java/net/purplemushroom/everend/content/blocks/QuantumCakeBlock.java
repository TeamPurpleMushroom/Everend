package net.purplemushroom.everend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumCakeBlock extends CakeBlock {
    public QuantumCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(BITES) > 0;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if (!pLevel.isClientSide) {
            int bites = pState.getValue(BITES);
            if (bites > 0) {
                pLevel.setBlock(pPos, pState.setValue(BITES, Integer.valueOf(bites - 1)), 3);
            }
        }
    }
}
