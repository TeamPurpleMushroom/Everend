package net.purplemushroom.everend.util.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

import java.lang.reflect.Field;

public class NonstopMeleeAttackGoal extends MeleeAttackGoal {
    public NonstopMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            Path path;
            path = this.mob.getNavigation().createPath(target, 0);
            try {
                Field field = MeleeAttackGoal.class.getDeclaredField("path");
                field.setAccessible(true);
                field.set(this, path);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            }
        }
        return false;
    }
}
