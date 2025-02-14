package net.purplemushroom.neverend.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.items.INESpecialAbilityItem;
import net.purplemushroom.neverend.content.items.ShifterineItemAbility;

@Mod.EventBusSubscriber
public class ItemAbilityEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void playerPickupEvent(EntityItemPickupEvent event) { // WARNING: if another mod adds an equally low priority event handler that happens to cancel the event, the ability will break!
        ItemStack stack = event.getItem().getItem();
        if (stack.getItem() instanceof INESpecialAbilityItem item) {
            item.getAbility().pickupItem(event.getEntity(), stack);
        }
    }

    @SubscribeEvent
    public static void playerDropEvent(ItemTossEvent event) {
        if (event.getEntity().getItem().getItem() instanceof INESpecialAbilityItem item) {
            item.getAbility().dropItem(event.getPlayer(), event.getEntity(), false);
        }
    }

    @SubscribeEvent
    public static void dropsEvent(LivingDropsEvent event) {
        for (ItemEntity itemEntity : event.getDrops()) {
            if (itemEntity.getItem().getItem() instanceof INESpecialAbilityItem item) {
                item.getAbility().dropItem(event.getEntity(), itemEntity, true);
            }
        }
    }
}
