package net.purplemushroom.neverend.content.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

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
    public boolean isRepairable(ItemStack stack) {
        return ability.canRepair() && super.isRepairable(stack);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ability.handleItemDamage(entity, stack, super.damageItem(stack, amount, entity, onBroken));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (!ability.applyAttributes(stack)) return ImmutableMultimap.of();
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return ability.isDurabilityBarVisible(pStack, super.isBarVisible(pStack));
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return ability.getDurabilityBarWidth(pStack, super.getBarWidth(pStack));
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return ability.getDurabilityBarColor(super.getBarColor(pStack));
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
