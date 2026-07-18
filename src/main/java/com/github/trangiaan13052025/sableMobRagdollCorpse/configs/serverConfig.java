package com.github.trangiaan13052025.sableMobRagdollCorpse.configs;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class serverConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> IGNORED_ENTITY_IDS;

    static {
        BUILDER
                .comment("Server config")
                .translation("config.sablemobragdollcorpse.category.server_config")
                .push("ServerConfig");

        IGNORED_ENTITY_IDS = BUILDER
                .comment("A list of mob ids to ignore from creating corpse in case it is causing a bug")
                .translation("config.sablemobragdollcorpse.ignored_entity_ids")
                .defineListAllowEmpty(
                        "ignoredEntityIds",
                        List.of(

                        ),
                        obj -> {
                            if (!(obj instanceof String stringId)) return false;
                            return ResourceLocation.tryParse(stringId) != null;
                        }
                );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
