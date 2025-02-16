package net.purplemushroom.neverend.content.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class NESpecialAbilityResource extends Item implements INESpecialAbilityItem {
    private final NEItemAbility ability;

    public NESpecialAbilityResource(Properties pProperties, NEItemAbility ability) {
        super(pProperties);
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
