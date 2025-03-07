package net.purplemushroom.everend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.purplemushroom.everend.content.blocks.tile.EndAltarBlockEntity;
import net.purplemushroom.everend.registry.EEBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

//TODO: - add comparator signal output
//      - add NBT tags to BlockEntity
//      - finish functionality (probs Diamond's territory)
public class EndAltarBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty POWERED;
    public static final BooleanProperty ALTAR_ACTIVE;
    private final BiFunction<BlockPos, BlockState, BlockEntity> tileFactory;

    public EndAltarBlock(Properties pProperties, BiFunction<BlockPos, BlockState, BlockEntity> tileFactory) {
        super(pProperties);
        this.tileFactory = tileFactory;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(ALTAR_ACTIVE, false));
    }

    @Override
    public InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!player.level().isClientSide() && player.level().getBlockEntity(blockPos) instanceof EndAltarBlockEntity entity) {
            if (hand == InteractionHand.MAIN_HAND && !player.getMainHandItem().isEmpty()) {
                entity.addItem(player.getMainHandItem());
            } else if (player.getMainHandItem().isEmpty() && !player.getOffhandItem().isEmpty()) {
                entity.addItem(player.getOffhandItem());
            } else if (hand == InteractionHand.MAIN_HAND) {
                entity.dropItem();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        ItemStack itemInHand = pContext.getItemInHand();
        Player player = pContext.getPlayer();
        boolean isActive = false;
        if (!level.isClientSide && player != null && player.canUseGameMasterBlocks()) {
            CompoundTag blockEntityData = BlockItem.getBlockEntityData(itemInHand);
            if (blockEntityData != null && blockEntityData.contains("AltarActive")) { //TODO: Tag doesn't exist rn, so initialize this tag in the BlockEntity
                isActive = true;
            }
        }

        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(ALTAR_ACTIVE, isActive);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED, ALTAR_ACTIVE);
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
        return createTicker(level_, blockEntityType_, EEBlockEntities.END_ALTAR);
    }

    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level_, BlockEntityType<T> givenType_, BlockEntityType<? extends EndAltarBlockEntity> expectedType_) {
        return level_.isClientSide ? null : createTickerHelper(givenType_, expectedType_, EndAltarBlockEntity::serverTick);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        POWERED = BlockStateProperties.POWERED;
        ALTAR_ACTIVE = BlockStateProperties.HAS_BOOK;
    }
}
