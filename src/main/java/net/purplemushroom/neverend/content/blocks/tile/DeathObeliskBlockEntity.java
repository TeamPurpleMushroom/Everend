package net.purplemushroom.neverend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.neverend.registry.NEBlockEntities;
import org.jetbrains.annotations.NotNull;

public class DeathObeliskBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public DeathObeliskBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(NEBlockEntities.DEATH_OBELISK, pPos, pBlockState);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> nonNullList) {
        inventory = nonNullList;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.neverend.death_obelisk");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DeathObeliskBlockEntity blockEntity) {
    }
}
