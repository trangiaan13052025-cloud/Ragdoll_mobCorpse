package com.github.trangiaan13052025.sableMobRagdollCorpse.data;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.Set;

public class ignoredDamageTypes {
    private static final Set<ResourceKey<DamageType>> IGNORED_DAMAGE_TYPES = Set.of(
            DamageTypes.CRAMMING,
            DamageTypes.FELL_OUT_OF_WORLD,
            DamageTypes.OUTSIDE_BORDER,

            DamageTypes.WITHER,
            DamageTypes.SONIC_BOOM,

            DamageTypes.LAVA,
            DamageTypes.GENERIC_KILL
    );

    public static boolean shouldIgnore(DamageSource source) {
        ResourceKey<DamageType> key = source.typeHolder()
                .unwrapKey()
                .orElse(null);

        //if (key != null && key.location().getNamespace().equals("create")) {
        //    return true;
        //}
        return IGNORED_DAMAGE_TYPES.contains(key);
    }
}
