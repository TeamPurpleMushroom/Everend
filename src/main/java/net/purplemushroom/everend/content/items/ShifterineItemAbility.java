package net.purplemushroom.everend.content.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.purplemushroom.everend.capability.player.EEPlayer;
import net.purplemushroom.everend.util.EntityUtil;

public class ShifterineItemAbility extends NEItemAbility {
    public static final ShifterineItemAbility INSTANCE = new ShifterineItemAbility();

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
        EEPlayer playerCap;
        if (dropper instanceof ServerPlayer && (playerCap = EEPlayer.from((ServerPlayer) dropper)) != null && playerCap.playerTracker.getLastGroundPos() != null) {
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
