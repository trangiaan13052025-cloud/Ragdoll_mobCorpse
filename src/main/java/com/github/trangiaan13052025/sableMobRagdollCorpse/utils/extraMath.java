package com.github.trangiaan13052025.sableMobRagdollCorpse.utils;

import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class extraMath {
    public static Vec3 randomDirection(RandomSource random) {
        double rawX = random.nextGaussian();
        double rawY = random.nextGaussian();
        double rawZ = random.nextGaussian();

        return new Vec3(rawX, rawY, rawZ).normalize();
    }

    public static Vec3 toVector(Float number) {
        return new Vec3(number, number, number);
    }
}
