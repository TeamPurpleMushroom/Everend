package net.purplemushroom.neverend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.LuduniteItemAbility;
import net.purplemushroom.neverend.registry.NEBlockEntities;
import net.purplemushroom.neverend.registry.NEItems;

public class EndAltarBlockEntity extends BlockEntity {
    //TODO: correct interaction results for clicking this block
    //TODO: fix weird placement behavior (i.e. when you try adding blocks to the altar)
    //TODO: make sure dust can only be added for the appropriate items
    //TODO: remove debug ticking code when done
    private ItemStack placedItem = ItemStack.EMPTY;
    private int dustCount = 0;

    public EndAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(NEBlockEntities.END_ALTAR, pPos, pBlockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, EndAltarBlockEntity blockEntity) {
        blockEntity.tick();
    }

    public void tick() {
        System.out.println(dustCount);
    }

    public void addItem(ItemStack stack) {
        if (stack.getItem() != NEItems.ENDERIUM_DUST) {
            if (!placedItem.isEmpty()) dropItem();
            placedItem = stack.copyWithCount(1);
            stack.shrink(1);
        } else if (!placedItem.isEmpty()) {
            if (dustCount < 10) {
                stack.shrink(1);
                dustCount++;

                if (dustCount == 10 && placedItem.getItem() instanceof INESpecialAbilityItem abilityItem && abilityItem.getAbility() == LuduniteItemAbility.INSTANCE && placedItem.isDamageableItem()) {
                    if (placedItem.hasTag() && placedItem.getTag().getInt("Charge") == placedItem.getMaxDamage()) return;

                    placedItem.getOrCreateTag().putInt("Charge", placedItem.getMaxDamage());
                    dustCount = 0;
                }
            }
        }
    }

    public void dropItem() {
        if (!placedItem.isEmpty()) {
            BlockPos pos = this.worldPosition.above();
            ItemEntity entity = new ItemEntity(this.level, pos.getX(), pos.getY(), pos.getZ(), placedItem);
            entity.setDefaultPickUpDelay();
            this.level.addFreshEntity(entity);
            placedItem = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Item", placedItem.serializeNBT());
        pTag.putInt("Dust", dustCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        placedItem = ItemStack.of((CompoundTag) pTag.get("Item"));
        dustCount = pTag.getInt("Dust");
    }
}
