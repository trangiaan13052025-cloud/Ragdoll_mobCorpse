package com.github.trangiaan13052025.sableMobRagdollCorpse;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTags {

    // This holds the reference to your blacklist.json file
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("mob_ragdoll_corpse", "blacklist")
    );
}
