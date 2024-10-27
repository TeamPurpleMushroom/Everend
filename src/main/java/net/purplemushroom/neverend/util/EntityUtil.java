package net.purplemushroom.neverend.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
                entity.teleportTo(pX, d3, pZ);
                if (level.noCollision(entity) && !level.containsAnyLiquid(entity.getBoundingBox())) {
                    flag = true;
                }
            }
        }

        if (!flag) {
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
}
