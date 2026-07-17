package com.github.trangiaan13052025.sableMobRagdollCorpse.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.Set;

public class shortLivedEntities {
    private static final Set<String> shortLivedEntities = Set.of(
            "minecraft:iron_golem",
            "minecraft:snow_golem"
    );

    public static boolean entityCorpseLife_Short(Entity entity) {
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        String entityIdString = entityId.toString();

        if (shortLivedEntities.contains(entityIdString)) {
            return true;
        }
        return false;
    }
}
