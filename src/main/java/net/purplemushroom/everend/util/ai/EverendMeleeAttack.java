package net.purplemushroom.everend.util.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

import java.lang.reflect.Field;

public class EverendMeleeAttack extends MeleeAttackGoal {
    private final int attackWarmupTime;
    private int attackTimer = 0;

    public EverendMeleeAttack(PathfinderMob pMob, double pSpeedModifier, int attackWarmup) {
        super(pMob, pSpeedModifier, false);
        attackWarmupTime = adjustedTickDelay(attackWarmup);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.path = this.mob.getNavigation().createPath(target, 0);
            if (this.path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            }
        }
        return false;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d0) {
            /*this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(pEnemy);*/
            if (this.getTicksUntilNextAttack() <= 0) {
                if (attackTimer >= attackWarmupTime) {
                    this.resetAttackCooldown();
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    this.mob.doHurtTarget(pEnemy);
                } else {
                    attackTimer++;
                }
            }
        } else {
            attackTimer = 0;
        }
    }
}
