package net.purplemushroom.neverend.content.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.purplemushroom.neverend.util.BitUtil;

public class AldoresItemAbility extends NEItemAbility {
    public static final AldoresItemAbility INSTANCE = new AldoresItemAbility();

    @Override
    public void onInventoryTick(Entity entity, ItemStack stack) {
        if (entity.tickCount % 20 == 0 && entity instanceof LivingEntity living) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (living.getItemBySlot(slot) == stack) {
                    stack.hurtAndBreak(1, living, (player) -> {
                        player.broadcastBreakEvent(slot);
                    });
                    return;
                }
            }
            stack.hurtAndBreak(1, living, (player) -> {});
        }
    }

    @Override
    public boolean acceptsEnchantment(Enchantment enchant) {
        return enchant != Enchantments.MENDING;
    }

    @Override
    public int getDurabilityBarColor(int originalColor) {
        return BitUtil.rgbToInt(237, 191, 64);
    }
}
