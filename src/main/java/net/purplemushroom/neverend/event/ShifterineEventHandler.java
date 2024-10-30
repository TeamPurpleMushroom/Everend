package net.purplemushroom.neverend.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.items.ShifterineItem;

@Mod.EventBusSubscriber
public class ShifterineEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void playerPickupEvent(EntityItemPickupEvent event) { // WARNING: if another mod adds an equally low priority event handler that happens to cancel the event, the ability will break!
        ItemStack stack = event.getItem().getItem();
        if (stack.getItem() instanceof ShifterineItem) {
            ((ShifterineItem) stack.getItem()).removeTag(stack);
        }
    }

    @SubscribeEvent
    public static void playerDropEvent(ItemTossEvent event) {
        handleItemDrop(event.getPlayer(), event.getEntity());
    }

    @SubscribeEvent
    public static void dropsEvent(LivingDropsEvent event) {
        for (ItemEntity item : event.getDrops()) {
            handleItemDrop(event.getEntity(), item);
        }
    }

    private static void handleItemDrop(LivingEntity dropper, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.getItem() instanceof ShifterineItem) {
            int y;
            NEPlayer playerCap;
            if (dropper instanceof ServerPlayer && (playerCap = NEPlayer.from((ServerPlayer) dropper)) != null) {
                y = playerCap.playerTracker.getLastGroundPos().getY();
            } else {
                y = (int) (dropper.getEyeY() + (double) dropper.fallDistance);
            }
            ((ShifterineItem) stack.getItem()).setTag(stack, y);
        }
    }
}
