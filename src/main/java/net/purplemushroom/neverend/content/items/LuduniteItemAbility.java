package net.purplemushroom.neverend.content.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.purplemushroom.neverend.util.BitUtil;

public class LuduniteItemAbility extends NEItemAbility {
    public static final LuduniteItemAbility INSTANCE = new LuduniteItemAbility();

    @Override
    public boolean acceptsEnchantment(Enchantment enchant) {
        return enchant != Enchantments.MENDING;
    }

    @Override
    public boolean canRepair() {
        return false;
    }

    @Override
    public int handleItemDamage(LivingEntity entity, ItemStack stack, int amount) {
        if (stack.hasTag() && stack.getTag().getInt("Charge") > 0) {
            int prevDamage = stack.getDamageValue();
            stack.hurt(amount, entity.getRandom(), entity instanceof ServerPlayer ? (ServerPlayer)entity : null);
            amount = stack.getDamageValue() - prevDamage;
            stack.getOrCreateTag().putInt("Charge", stack.getOrCreateTag().getInt("Charge") - amount);
        }
        stack.setDamageValue(0);
        return 0;
    }

    @Override
    public boolean applyAttributes(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getInt("Charge") > 0;
    }

    @Override
    public boolean isDurabilityBarVisible(ItemStack stack, boolean originallyVisible) {
        return stack.getMaxDamage() > 0;
    }

    @Override
    public int getDurabilityBarWidth(ItemStack stack, int originalWidth) {
        return Math.round((float)stack.getOrCreateTag().getInt("Charge") * 13.0F / (float)stack.getMaxDamage());
    }

    @Override
    public int getDurabilityBarColor(int originalColor) {
        return BitUtil.rgbToInt(225, 127, 245);
    }
}
