package com.github.trangiaan13052025.sableMobRagdollCorpse.data;

import com.github.trangiaan13052025.sableMobRagdollCorpse.configs.serverConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import java.util.List;
import java.util.Set;

public class ignoredEntities {
    private static final Set<String> BLACKLIST = Set.of(
            "minecraft:ender_dragon",
            "minecraft:warden",
            "minecraft:wither",

            "minecraft:vex",
            "minecraft:allay",
            "minecraft:breeze",

            "minecraft:ghast",

            "minecraft:slime",
            "minecraft:magma_cube",

            "minecraft:phantom",
            "minecraft:bat"
    );

    public static boolean shouldIgnoreEntity(Entity entity) {
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        String entityIdString = entityId.toString();

        if (BLACKLIST.contains(entityIdString)) {
            return true;
        }

        List<? extends String> configList = serverConfig.IGNORED_ENTITY_IDS.get();
        return configList.contains(entityIdString);
    }
}
