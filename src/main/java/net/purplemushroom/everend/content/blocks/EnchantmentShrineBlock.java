package net.purplemushroom.everend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.purplemushroom.everend.registry.EEItems;
import net.purplemushroom.everend.util.InventoryUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

//TODO:
// - set `ShrineActive` tag for BlockEntity, for when the altar is actively enchanting (maybe have active texture / model variant)
// - have `CrystalAmount` states for each # of crystals that are inserted (have texture update for each crystal # variant)
// - create BlockEntity for better shrine config / tag saving + `ShrineActive` & `CrystalAmount` blockstate changes
// - change signal output to be based off of # of items in shrine + if shrine is actively enchanting &/or if finished enchanted item is still present

public class EnchantmentShrineBlock extends Block {
    public static final DirectionProperty FACING;
    public static final BooleanProperty POWERED;
    public static final BooleanProperty SHRINE_ACTIVE;

    public EnchantmentShrineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(SHRINE_ACTIVE, false));
    }

    protected boolean stackIsShrineMarked(ItemStack stack, CompoundTag tag) {
        return stack.hasTag() && tag != null && tag.getBoolean("ShrineEnchanted");
    }
    //TODO: ^^^
    //      To be used with BlockEntity for when that's added (will be useful to avoid dupes and stuff)
    //      vvv
    protected boolean stackIsEnchantedNotMarked(ItemStack stack, CompoundTag tag) {
        return stack.isEnchanted() && !stackIsShrineMarked(stack, tag);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        ItemStack heldStack = player.getMainHandItem();
        CompoundTag heldStackTag = heldStack.getTag();
        if (stackIsEnchantedNotMarked(heldStack, heldStackTag)) {
            if (InventoryUtil.consumeItem(player, EEItems.ENCHANTMENT_CRYSTAL)) { //TODO: convert to DataTag so modpack devs can add their own item
                if (!level.isClientSide()) {
                    boolean enchanted = false;
                    Map<Enchantment, Integer> enchantments = heldStack.getAllEnchantments();
                    for (Map.Entry<Enchantment, Integer> enchantmentEntry : enchantments.entrySet()) {
                        Enchantment enchant = enchantmentEntry.getKey();
                        if (enchant.getMaxLevel() > 1) {
                            enchantments.put(enchant, enchantmentEntry.getValue() + 1);
                            enchanted = true;
                        }
                    }
                    if (enchanted) {
                        EnchantmentHelper.setEnchantments(enchantments, heldStack);
                        heldStack.getOrCreateTag().putBoolean("ShrineEnchanted", true);
                        level.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                        scheduleSignalChange(level, blockPos, blockState, true);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    } else {
                        scheduleSignalChange(level, blockPos, blockState, false);
                    }
                }
            }
        }
        return super.use(blockState, level, blockPos, player, hand, hitResult);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        ItemStack itemInHand = pContext.getItemInHand();
        Player player = pContext.getPlayer();
        boolean isActive = false;
        if (!level.isClientSide && player != null && player.canUseGameMasterBlocks()) {
            CompoundTag blockEntityData = BlockItem.getBlockEntityData(itemInHand);
            if (blockEntityData != null && blockEntityData.contains("ShrineActive")) { //TODO: Tag doesn't exist rn, initialize this tag in the BlockEntity when we get there
                isActive = true;
            }
            //TODO: add state and tag for `CrystalAmount` as well
        }

        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(SHRINE_ACTIVE, isActive);
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
        pBuilder.add(FACING, POWERED, SHRINE_ACTIVE);
    }

    //Schedules update to Redstone output signal depending on whether the Shrine has finished enchanting or if it never started
    public static void scheduleSignalChange(Level level, BlockPos blockPos, BlockState blockState, boolean powered) {
        setPoweredState(level, blockPos, blockState, powered);
        level.scheduleTick(blockPos, blockState.getBlock(), 2);
        level.levelEvent(1043, blockPos, 0);
    }

    private static void setPoweredState(Level level, BlockPos blockPos, BlockState blockState, boolean powered) {
        level.setBlock(blockPos, blockState.setValue(POWERED, powered), 3);
        updateBelow(level, blockPos, blockState);
    }

    private static void updateBelow(Level level, BlockPos blockPos, BlockState blockState) {
        level.updateNeighborsAt(blockPos.below(), blockState.getBlock());
    }

    @Override
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        setPoweredState(serverLevel, blockPos, blockState, false);
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (!blockState.is(newState.getBlock())) {
            if (blockState.getValue(POWERED)) {
                level.updateNeighborsAt(blockPos.below(), this);
            }
            //TODO: drop items if destroyed
            super.onRemove(blockState, level, blockPos, newState, isMoving);
        }
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }
    //TODO: ^^^
    //      change these to vary signal depending on what / how many items are in altar (+1/item)
    //      + add boost if shrine is active (+2)
    //      or if finished enchanted item is present (+3/item)
    //      vvv

    @Override
    public int getDirectSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        return direction == Direction.UP && blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos) {
        //TODO: change this, so that # of normal items in shrine affect signal level linearly, while "enchanted" items and/or active shrine add extra boost to signal
        if (blockState.getValue(SHRINE_ACTIVE)) {
            return 15;
        }
        return 0;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        POWERED = BlockStateProperties.POWERED;
        SHRINE_ACTIVE = BlockStateProperties.HAS_BOOK;
    }
}
