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

import javax.naming.CompoundName;

public class ShifterineItem extends Item {
    public ShifterineItem() {
        super(new Item.Properties());
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
        CompoundTag tag = p_41404_.getOrCreateTag();
        if (p_41406_.onGround()) tag.putInt("LastHolderY", (int) p_41406_.getEyeY());
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (stack.hasTag()) {
            int y = stack.getTag().getInt("LastHolderY");
            double despawnY = (double)(entity.level().getMinBuildHeight() - 64);
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
