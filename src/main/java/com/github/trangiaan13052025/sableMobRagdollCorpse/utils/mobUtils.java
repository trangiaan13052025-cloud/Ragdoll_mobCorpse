package com.github.trangiaan13052025.sableMobRagdollCorpse.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;

public class mobUtils {
    public static boolean isTamed(Entity entity) {
        // Check if the entity is capable of being tamed (Wolf, Cat, Parrot, etc.)
        if (entity instanceof TamableAnimal tamable) {
            // Return true if the animal is actively tamed by a player
            return tamable.isTame();
        }

        // If it's not a tamable type of entity, return false
        return false;
    }
}
