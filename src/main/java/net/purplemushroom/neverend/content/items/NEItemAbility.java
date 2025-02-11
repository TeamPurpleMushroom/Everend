package net.purplemushroom.neverend.content.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class NEItemAbility {
    protected NEItemAbility() {

    }

    public void onInventoryTick(ItemStack stack) {}

    public void onDroppedTick(ItemStack stack, ItemEntity entity) {}

    public void pickupItem(Player player, ItemStack item) {}

    public void dropItem(LivingEntity dropper, ItemEntity droppedItem, boolean death) {}
}
