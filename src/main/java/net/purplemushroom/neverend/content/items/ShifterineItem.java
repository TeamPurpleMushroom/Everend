package net.purplemushroom.neverend.content.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.naming.CompoundName;

public class ShifterineItem extends Item {
    public ShifterineItem() {
        super(new Item.Properties());
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(itemStack, level, entity, p_41407_, p_41408_);
        CompoundTag tag = itemStack.getOrCreateTag();
        if (entity.onGround()) tag.putInt("LastHolderY", (int) entity.getEyeY());
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (stack.hasTag() && stack.getTag() != null) {
            int y = stack.getTag().getInt("LastHolderY");
            double despawnY = entity.level().getMinBuildHeight() - 64;
            if (entity.getY() < y && entity.level().clip(
                    new ClipContext(entity.position(), new Vec3(entity.getX(), despawnY, entity.getZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity))
                    .getType() == HitResult.Type.MISS) {
                if (entity.getY() < despawnY) {
                    entity.setPos(entity.getX(), despawnY, entity.getZ());
                }
                entity.push(0.0, 0.1, 0.0);
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
