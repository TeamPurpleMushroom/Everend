package net.purplemushroom.neverend.content.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.purplemushroom.neverend.capability.player.NEPlayer;
import net.purplemushroom.neverend.util.BitUtil;
import net.purplemushroom.neverend.util.EntityUtil;

public class DragonboneItemAbility extends NEItemAbility {
    public static final DragonboneItemAbility INSTANCE = new DragonboneItemAbility();

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

    @Override
    public void onDroppedTick(ItemStack stack, ItemEntity entity) {
        if (!(stack.hasTag() && stack.getTag().contains("LastHolderY"))) {
            setTag(stack, (int) entity.getY());
        }
        int y = stack.getTag().getInt("LastHolderY");
        double despawnY = entity.level().getMinBuildHeight() - 64;
        if (entity.getY() < y && EntityUtil.isOverVoid(entity)) {
            if (entity.getY() < despawnY) {
                entity.setPos(entity.getX(), despawnY, entity.getZ());
            }
            entity.push(0.0, 0.1, 0.0);
        }
    }

    @Override
    public void pickupItem(Player player, ItemStack item) {
        removeTag(item);
    }

    @Override
    public void dropItem(LivingEntity dropper, ItemEntity droppedItem, boolean death) {
        ItemStack stack = droppedItem.getItem();
        int y;
        NEPlayer playerCap;
        if (dropper instanceof ServerPlayer && (playerCap = NEPlayer.from((ServerPlayer) dropper)) != null && playerCap.playerTracker.getLastGroundPos() != null) {
            y = playerCap.playerTracker.getLastGroundPos().getY();
        } else {
            y = (int) (Mth.floor(dropper.getY()) + (double) dropper.fallDistance);
        }
        setTag(stack, y);
    }

    private void setTag(ItemStack stack, int y) {
        stack.getOrCreateTag()
                .putInt("LastHolderY", y + 1);
    }

    private void removeTag(ItemStack stack) {
        stack.removeTagKey("LastHolderY");
    }
}
