package net.purplemushroom.everend.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityUtil {

    /**
     * Same as the randomTeleport() method from {@link net.minecraft.world.entity.LivingEntity}
     * Adapted to be used for any Entity
     *
     * @param entity             The entity being teleported
     * @param pX                 The initial X value to be teleported to
     * @param pY                 The initial Y value to be teleported to
     * @param pZ                 The initial Z value to be teleported to
     * @param pBroadcastTeleport Broadcast teleport
     * @return Returns true or false depending on if the projected location is suitable and exists
     */
    public static boolean randomTeleport(Entity entity, double pX, double pY, double pZ, boolean pBroadcastTeleport) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();
        double d3 = pY;
        boolean flag = false;
        BlockPos blockpos = BlockPos.containing(pX, pY, pZ);
        Level level = entity.level();
        if (level.hasChunkAt(blockpos)) {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > level.getMinBuildHeight()) {
                BlockPos blockPos = blockpos.below();
                BlockState belowState = level.getBlockState(blockPos);
                if (belowState.blocksMotion()) {
                    flag1 = true;
                } else {
                    --d3;
                    blockpos = blockPos;
                }
            }

            if (flag1) {
                entity.resetFallDistance();
                entity.teleportTo(pX, d3, pZ);
                if (level.noCollision(entity) && !level.containsAnyLiquid(entity.getBoundingBox())) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            entity.resetFallDistance();
            entity.teleportTo(entityX, entityY, entityZ);
            return false;
        } else {
            if (pBroadcastTeleport) {
                level.broadcastEntityEvent(entity, (byte) 46);
            }

            if (entity instanceof PathfinderMob pathfinderMob) {
                pathfinderMob.getNavigation().stop();
            }

            return true;
        }
    }

    public static boolean isOverVoid(Entity entity) {
        Level level = entity.level();
        AABB belowBB = entity.getBoundingBox().setMinY(level.getMinBuildHeight()).inflate(0.1D, 0, 0.1D);
        if (belowBB.maxY > level.getMaxBuildHeight()) belowBB.setMaxY(level.getMaxBuildHeight());
        return entity.level().getBlockStatesIfLoaded(belowBB).allMatch(BlockBehaviour.BlockStateBase::isAir);
    }

    public static boolean isAtStableLocation(Entity entity) {
        return !isOverVoid(entity) && entity.onGround() && !entity.level().getBlockState(entity.blockPosition().below()).isAir();
    }

    public static boolean isHolding(LivingEntity entity, Item item) {
        return entity.getMainHandItem().getItem() == item || entity.getOffhandItem().getItem() == item;
    }

    public static Vec3 getCenterPos(Entity entity) {
        return entity.position().add(0.0, entity.getBbHeight() / 2, 0.0);
    }

    public static double lookingAt(LivingEntity entity, Vec3 lookTarget) {
        Vec3 eyes = entity.getEyePosition();
        Vec3 targetVector = lookTarget.subtract(eyes).normalize();
        Vec3 lookVector = entity.getLookAngle();
        return lookVector.dot(targetVector);
    }

    public static boolean isLookingAt(LivingEntity subject, BlockPos targetPos) {
        Vec3 viewVector = subject.getViewVector(1.0F).normalize();
        Vec3 distance = new Vec3(targetPos.getX() - subject.getX(), targetPos.getCenter().y() - subject.getEyeY(), targetPos.getZ() - subject.getZ());
        double length = distance.length();
        distance = distance.normalize();
        double dotted = viewVector.dot(distance);
        return dotted > 1.0 - 0.025 / length && hasLineOfSight(subject.level(), subject, targetPos);
    }

    public static boolean hasLineOfSight(Level level, LivingEntity subject, BlockPos targetPos) {
        Vec3 subjectVector = new Vec3(subject.getX(), subject.getEyeY(), subject.getZ());
        Vec3 targetVector = new Vec3(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        if (targetVector.distanceTo(subjectVector) > 128.0) {
            return false;
        } else {
            return level.clip(new ClipContext(subjectVector, targetVector, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, subject)).getType() == HitResult.Type.MISS;
        }
    }
}
