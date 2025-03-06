package net.purplemushroom.everend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.everend.content.blocks.tile.GazeCrystalBlockEntity;
import net.purplemushroom.everend.registry.EEBlockEntities;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class GazeCrystalBlock extends Block implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, BlockEntity> tileFactory;

    public GazeCrystalBlock(Properties pProperties, BiFunction<BlockPos, BlockState, BlockEntity> tileFactory) {
        super(pProperties);
        this.tileFactory = tileFactory;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return tileFactory.apply(blockPos, blockState);
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> givenType_, BlockEntityType<E> expectedType_, BlockEntityTicker<? super E> ticker_) {
        return expectedType_ == givenType_ ? (BlockEntityTicker<A>) ticker_ : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level_, BlockState state_, BlockEntityType<T> blockEntityType_) {
        return createTicker(level_, blockEntityType_, EEBlockEntities.GAZE_CRYSTAL);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level_, BlockEntityType<T> givenType_, BlockEntityType<? extends GazeCrystalBlockEntity> expectedType_) {
        return level_.isClientSide ? null : createTickerHelper(givenType_, expectedType_, GazeCrystalBlockEntity::serverTick);
    }
}
