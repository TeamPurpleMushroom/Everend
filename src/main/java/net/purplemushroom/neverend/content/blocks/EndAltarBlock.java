package net.purplemushroom.neverend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.purplemushroom.neverend.content.blocks.tile.EndAltarBlockEntity;
import net.purplemushroom.neverend.registry.NEBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class EndAltarBlock extends Block implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, BlockEntity> tileFactory;

    public EndAltarBlock(Properties pProperties, BiFunction<BlockPos, BlockState, BlockEntity> tileFactory) {
        super(pProperties);
        this.tileFactory = tileFactory;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.level().isClientSide() && pPlayer.level().getBlockEntity(pPos) instanceof EndAltarBlockEntity entity) {
            if (pHand == InteractionHand.MAIN_HAND && !pPlayer.getMainHandItem().isEmpty()) {
                entity.addItem(pPlayer.getMainHandItem());
            } else if (pPlayer.getMainHandItem().isEmpty() && !pPlayer.getOffhandItem().isEmpty()) {
                entity.addItem(pPlayer.getOffhandItem());
            } else if (pHand == InteractionHand.MAIN_HAND) {
                entity.dropItem();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return tileFactory.apply(pPos, pState);
    }

    @javax.annotation.Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> givenType_, BlockEntityType<E> expectedType_, BlockEntityTicker<? super E> ticker_) {
        return expectedType_ == givenType_ ? (BlockEntityTicker<A>) ticker_ : null;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level_, BlockState state_, BlockEntityType<T> blockEntityType_) {
        return createTicker(level_, blockEntityType_, NEBlockEntities.END_ALTAR);
    }

    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level_, BlockEntityType<T> givenType_, BlockEntityType<? extends EndAltarBlockEntity> expectedType_) {
        return level_.isClientSide ? null : createTickerHelper(givenType_, expectedType_, EndAltarBlockEntity::serverTick);
    }
}
