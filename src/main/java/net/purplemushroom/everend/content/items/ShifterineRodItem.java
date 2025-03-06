package net.purplemushroom.everend.content.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.capability.player.EEPlayer;
import net.purplemushroom.everend.capability.player.data.RiftFishingData;
import net.purplemushroom.everend.content.entities.FishingRift;
import net.purplemushroom.everend.registry.EEEntities;

public class ShifterineRodItem extends Item {
    public ShifterineRodItem() {
        super(new Properties().durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            Vec3 vector1 = player.getEyePosition();
            Vec3 vector2 = vector1.add(player.getViewVector(0.0f).scale(40.0));
            HitResult hitResult = ProjectileUtil.getEntityHitResult(player, vector1, vector2, player.getBoundingBox().inflate(50), (p_234237_) -> !p_234237_.isSpectator() && p_234237_.isPickable(), 40.0); // TODO: this is a mess
            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult) hitResult).getEntity();
                if (entity.getType() == EEEntities.FISHING_RIFT_TYPE.get()) {
                    EEPlayer playerCap = EEPlayer.from(player);
                    if (playerCap != null) {
                        RiftFishingData riftData = playerCap.riftFishingData;
                        if (!riftData.isActive()) {
                            riftData.startFishingFromRift((FishingRift) entity);
                            playerCap.detectAndSendChanges();
                            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                        }
                    }
                }
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }
}
