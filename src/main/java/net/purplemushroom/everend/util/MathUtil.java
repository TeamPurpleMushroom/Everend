package net.purplemushroom.everend.util;

import net.minecraft.world.phys.Vec3;

public class MathUtil {
    public static Vec3[] createBezierCurve(Vec3 start, Vec3 end, Vec3 midpoint, int nodeCount) {
        Vec3[] nodes = new Vec3[nodeCount];
        nodeCount--;
        for (int i = 0; i <= nodeCount; i++) {
            float lerpAmount = (float) i / nodeCount;
            Vec3 lerp1 = start.lerp(midpoint, lerpAmount);
            Vec3 lerp2 = midpoint.lerp(end, lerpAmount);
            nodes[i] = lerp1.lerp(lerp2, lerpAmount);
        }
        return nodes;
    }

    public static int min(int... nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < result) {
                result = nums[i];
            }
        }
        return result;
    }

    public static int max(int... nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > result) {
                result = nums[i];
            }
        }
        return result;
    }
}
