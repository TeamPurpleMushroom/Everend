package net.purplemushroom.everend.content.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class NEItemAbility {
    protected NEItemAbility() {

    }

    public void onInventoryTick(Entity holder, ItemStack stack) {

    }

    public boolean acceptsEnchantment(Enchantment enchant) {
        return true;
    }

    public boolean canRepair() {
        return true;
    }

    public int handleItemDamage(LivingEntity entity, ItemStack stack, int amount) {
        return amount;
    }

    public boolean applyAttributes(ItemStack stack) {
        return true;
    }

    public boolean isDurabilityBarVisible(ItemStack stack, boolean originallyVisible) {return originallyVisible;}

    public int getDurabilityBarWidth(ItemStack stack, int originalWidth) {return originalWidth;}

    public int getDurabilityBarColor(int originalColor) {
        return originalColor;
    }

    public void onDroppedTick(ItemStack stack, ItemEntity entity) {}

    public void pickupItem(Player player, ItemStack item) {}

    public void dropItem(LivingEntity dropper, ItemEntity droppedItem, boolean death) {}
}
