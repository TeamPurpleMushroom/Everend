package net.purplemushroom.everend.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    public static boolean hasItem(Player player, Item item) {
        if (player.getAbilities().instabuild) return true;
        Inventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack1 = inventory.getItem(i);
            if (itemstack1.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public static boolean consumeItem(Player player, Item item) {
        if (player.getAbilities().instabuild) return true;
        Inventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == item) {
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }
}
