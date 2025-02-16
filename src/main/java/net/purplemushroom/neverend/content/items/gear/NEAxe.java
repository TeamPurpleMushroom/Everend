package net.purplemushroom.neverend.content.items.gear;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.NEItemAbility;

public class NEAxe extends AxeItem implements INESpecialAbilityItem {
    private final NEItemAbility ability;

    public NEAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, NEItemAbility ability) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.ability = ability;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        ability.onInventoryTick(pEntity, pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return ability.acceptsEnchantment(enchantment) && super.canApplyAtEnchantingTable(stack, enchantment);
    }



    @Override
    public boolean isRepairable(ItemStack stack) {
        return ability.canRepair() && super.isRepairable(stack);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        int custom = ability.getDurabilityBar();
        if (custom >= 0) return custom;
        return super.getBarColor(pStack);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        ability.onDroppedTick(stack, entity);
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public NEItemAbility getAbility() {
        return ability;
    }
}
