package net.purplemushroom.neverend.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.neverend.content.blocks.tile.DeathObeliskBlockEntity;
import net.purplemushroom.neverend.registry.NEBlockEntities;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class DeathObeliskBlock extends NEContainerBlockEntity {
    public DeathObeliskBlock(Properties properties, BiFunction<BlockPos, BlockState, BlockEntity> tileFactory) {
        super(properties, tileFactory);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level_, BlockState state_, BlockEntityType<T> blockEntityType_) {
        return createTicker(level_, blockEntityType_, NEBlockEntities.DEATH_OBELISK);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level_, BlockEntityType<T> givenType_, BlockEntityType<? extends DeathObeliskBlockEntity> expectedType_) {
        return level_.isClientSide ? null : createTickerHelper(givenType_, expectedType_, DeathObeliskBlockEntity::serverTick);
    }
}
