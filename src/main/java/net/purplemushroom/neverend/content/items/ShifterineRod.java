package net.purplemushroom.neverend.content.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;
import net.purplemushroom.neverend.content.capability.player.data.RiftFishingData;
import net.purplemushroom.neverend.content.entities.Rift;
import net.purplemushroom.neverend.registry.NEEntities;
import net.purplemushroom.neverend.util.MathUtil;

public class ShifterineRod extends Item {
    public ShifterineRod() {
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
                if (entity.getType() == NEEntities.RIFT_TYPE.get()) {
                    NEPlayer playerCap = NEPlayer.from((ServerPlayer) player);
                    if (playerCap != null) {
                        RiftFishingData riftData = playerCap.riftFishingData;
                        if (!riftData.isActive(level)) {
                            riftData.startFishingFromRift((Rift) entity);
                            playerCap.detectAndSendChanges();
                            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
                        }
                    }
                    /*System.out.println("Hi 3");
                    for (Vec3 node : MathUtil.createBezierCurve(vector1, vector1.add(0, 10, 0), vector1.add(5, 5, 0), 10)) {
                        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, node.x, node.y, node.z, 10, 0, 0, 0, 0);
                        System.out.println("HIT!!!!");
                    }*/
                }
            }
        }
        return InteractionResultHolder.fail(itemStack);
    }
}
