package net.purplemushroom.neverend.content.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.purplemushroom.neverend.capability.player.NEPlayer;
import net.purplemushroom.neverend.util.EntityUtil;

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
        NEPlayer playerCap;
        if (dropper instanceof ServerPlayer && (playerCap = NEPlayer.from((ServerPlayer) dropper)) != null) {
            y = playerCap.playerTracker.getLastGroundPos().getY(); // FIXME: for some reason this can cause a nullpointerexception if you throw a shifterine crystal & haven't touched the ground since loading the world
        } else {
            y = (int) (dropper.getEyeY() + (double) dropper.fallDistance);
        }
        setTag(stack, y);
    }

    private void setTag(ItemStack stack, int y) {
        stack.getOrCreateTag()
                .putInt("LastHolderY", y);
    }

    private void removeTag(ItemStack stack) {
        stack.removeTagKey("LastHolderY");
    }
}
