package net.purplemushroom.neverend.content.items;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EndPorterItem extends Item {
    public EndPorterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel && pPlayer.canChangeDimensions()) {
            ResourceKey<Level> levelResourceKey = pLevel.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverLevel = ((ServerLevel) pLevel).getServer().getLevel(levelResourceKey);
            if (serverLevel == null) {
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }

            pPlayer.changeDimension(serverLevel);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
