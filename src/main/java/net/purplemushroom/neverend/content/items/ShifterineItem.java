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
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.naming.CompoundName;

public class ShifterineItem extends Item {
    public ShifterineItem() {
        super(new Item.Properties());
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!(stack.hasTag() && stack.getTag().contains("LastHolderY"))) {
            setTag(stack, (int) entity.getY());
        }
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
        return super.onEntityItemUpdate(stack, entity);
    }

    public void setTag(ItemStack stack, int y) {
        stack.getOrCreateTag()
                .putInt("LastHolderY", y); // TODO: use player capability
    }

    public void removeTag(ItemStack stack) {
        stack.removeTagKey("LastHolderY");
    }
}
