package com.github.trangiaan13052025.sableMobRagdollCorpse.compat;

import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class tacz {
    static float multiplier = 0.5f;
    public static Vec3 TaczMultiplier(Vec3 FirstHitForce, Entity bulletEntity) {
        if (isTacz(bulletEntity)) {
            return FirstHitForce.multiply(multiplier, multiplier, multiplier);
        } else {
            return FirstHitForce;
        }
    }

    public static Boolean isTacz(Entity bulletEntity) {
        return bulletEntity instanceof EntityKineticBullet;
    }
}
