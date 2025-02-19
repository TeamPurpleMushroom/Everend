package net.purplemushroom.neverend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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
