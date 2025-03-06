package net.purplemushroom.everend.content.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.registry.EEItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class EndAnchorBlock extends Block implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, BlockEntity> tileFactory;
    public static final IntegerProperty CHARGE;
    private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS;
    private static final ImmutableList<Vec3i> RESPAWN_OFFSETS;

    public EndAnchorBlock(BlockBehaviour.Properties pProperties, BiFunction<BlockPos, BlockState, BlockEntity> tileFactory) {
        super(pProperties);
        this.tileFactory = tileFactory;
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGE, 0));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (pHand == InteractionHand.MAIN_HAND && !isRespawnFuel(itemInHand) && isRespawnFuel(pPlayer.getItemInHand(InteractionHand.OFF_HAND))) {
            return InteractionResult.PASS;
        } else if (isRespawnFuel(itemInHand) && canBeCharged(pState)) {
            charge(pPlayer, pLevel, pPos, pState);
            if (!pPlayer.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else if (pState.getValue(CHARGE) == 0) {
            return InteractionResult.PASS;
        } else if (!canSetSpawn(pLevel)) {
            if (!pLevel.isClientSide) {
                this.explode(pState, pLevel, pPos);
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            if (!pLevel.isClientSide) {
                ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
                if (serverPlayer.getRespawnDimension() != pLevel.dimension() || !pPos.equals(serverPlayer.getRespawnPosition())) {
                    serverPlayer.setRespawnPosition(pLevel.dimension(), pPos, 0.0F, false, true);
                    pLevel.playSound(null, (double) pPos.getX() + 0.5, (double) pPos.getY() + 0.5, (double) pPos.getZ() + 0.5, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.CONSUME;
        }
    }

    private static boolean isRespawnFuel(ItemStack pStack) {
        return pStack.is(EEItems.SHIFTERINE_CRYSTAL);
    }

    private static boolean canBeCharged(BlockState pState) {
        return pState.getValue(CHARGE) < 4;
    }

    private static boolean isWaterThatWouldFlow(BlockPos pPos, Level pLevel) {
        FluidState fluidState = pLevel.getFluidState(pPos);
        if (!fluidState.is(FluidTags.WATER)) {
            return false;
        } else if (fluidState.isSource()) {
            return true;
        } else {
            float amount = (float) fluidState.getAmount();
            if (amount < 2.0F) {
                return false;
            } else {
                FluidState pLevelFluidState = pLevel.getFluidState(pPos.below());
                return !pLevelFluidState.is(FluidTags.WATER);
            }
        }
    }

    private void explode(BlockState pState, Level pLevel, final BlockPos pPos2) {
        pLevel.removeBlock(pPos2, false);
        Stream<Direction> directionStream = Direction.Plane.HORIZONTAL.stream();
        Objects.requireNonNull(pPos2);
        boolean anyMatch = directionStream.map(pPos2::relative).anyMatch((blockPos) -> isWaterThatWouldFlow(blockPos, pLevel));
        final boolean isWater = anyMatch || pLevel.getFluidState(pPos2.above()).is(FluidTags.WATER);
        ExplosionDamageCalculator explosionDamageCalculator = new ExplosionDamageCalculator() {
            public @NotNull Optional<Float> getBlockExplosionResistance(Explosion pExplosion, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
                return blockPos.equals(pPos2) && isWater ? Optional.of(Blocks.WATER.getExplosionResistance()) : super.getBlockExplosionResistance(pExplosion, blockGetter, blockPos, blockState, fluidState);
            }
        };
        Vec3 center = pPos2.getCenter();
        pLevel.explode(null, pLevel.damageSources().badRespawnPointExplosion(center), explosionDamageCalculator, center, 5.0F, true, Level.ExplosionInteraction.BLOCK);
    }

    public static boolean canSetSpawn(Level pLevel) {
        return pLevel.dimensionTypeRegistration().is(BuiltinDimensionTypes.END);
    }

    public static void charge(@Nullable Entity pEntity, Level pLevel, BlockPos pPos, BlockState pState) {
        BlockState blockState = pState.setValue(CHARGE, pState.getValue(CHARGE) + 1);
        pLevel.setBlock(pPos, blockState, 3);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockState));
        pLevel.playSound(null, (double) pPos.getX() + 0.5, (double) pPos.getY() + 0.5, (double) pPos.getZ() + 0.5, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(CHARGE) != 0) {
            if (pRandom.nextInt(100) == 0) {
                pLevel.playSound(null, (double) pPos.getX() + 0.5, (double) pPos.getY() + 0.5, (double) pPos.getZ() + 0.5, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double $$4 = (double) pPos.getX() + 0.5 + (0.5 - pRandom.nextDouble());
            double $$5 = (double) pPos.getY() + 1.0;
            double $$6 = (double) pPos.getZ() + 0.5 + (0.5 - pRandom.nextDouble());
            double $$7 = (double) pRandom.nextFloat() * 0.04;
            pLevel.addParticle(ParticleTypes.END_ROD, $$4, $$5, $$6, 0.0, $$7, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CHARGE);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public static int getScaledChargeLevel(BlockState pState, int pScale) {
        return Mth.floor((float) (pState.getValue(CHARGE)) / 4.0F * (float) pScale);
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return getScaledChargeLevel(pBlockState, 15);
    }

    public static Optional<Vec3> findStandUpPosition(EntityType<?> pEntityType, CollisionGetter pLevel, BlockPos pPos) {
        Optional<Vec3> standUpPosition = findStandUpPosition(pEntityType, pLevel, pPos, true);
        return standUpPosition.isPresent() ? standUpPosition : findStandUpPosition(pEntityType, pLevel, pPos, false);
    }

    private static Optional<Vec3> findStandUpPosition(EntityType<?> pEntityType, CollisionGetter pLevel, BlockPos pPos, boolean pSimulate) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        UnmodifiableIterator<Vec3i> var5 = RESPAWN_OFFSETS.iterator();

        Vec3 vec3;
        do {
            if (!var5.hasNext()) {
                return Optional.empty();
            }

            Vec3i $$5 = var5.next();
            mutableBlockPos.set(pPos).move($$5);
            vec3 = DismountHelper.findSafeDismountLocation(pEntityType, pLevel, mutableBlockPos, pSimulate);
        } while (vec3 == null);

        return Optional.of(vec3);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    static {
        CHARGE = BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
        RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
        RESPAWN_OFFSETS = (new ImmutableList.Builder()).addAll(RESPAWN_HORIZONTAL_OFFSETS).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator()).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator()).add(new Vec3i(0, 1, 0)).build();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return tileFactory.apply(blockPos, blockState);
    }
}
