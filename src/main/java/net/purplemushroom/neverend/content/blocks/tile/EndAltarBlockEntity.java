package net.purplemushroom.neverend.content.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.LuduniteItemAbility;
import net.purplemushroom.neverend.registry.NEBlockEntities;
import net.purplemushroom.neverend.registry.NEItems;
import net.purplemushroom.neverend.util.EntityUtil;

import java.util.List;

public class EndAltarBlockEntity extends BlockEntity {
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

    public void useItem(ItemStack stack) {
        if (stack.getItem() == NEItems.ENDERIUM_DUST) {
            if (dustCount < 10) {
                stack.shrink(1);
                dustCount++;
            }
        } else if (stack.getItem() instanceof INESpecialAbilityItem abilityItem && abilityItem.getAbility() == LuduniteItemAbility.INSTANCE && stack.isDamageableItem()) {
            if (stack.hasTag() && stack.getTag().getInt("Charge") == stack.getMaxDamage()) return;

            stack.getOrCreateTag().putInt("Charge", stack.getMaxDamage());
            dustCount = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Dust", dustCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        dustCount = pTag.getInt("Dust");
    }
}
