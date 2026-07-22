package com.github.trangiaan13052025.sableMobRagdollCorpse.configs;
import net.neoforged.neoforge.common.ModConfigSpec;

public class clientConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue BLOOD_EFFECTS;

    static {
        BUILDER
                .comment("Client config")
                .translation("config.sablemobragdollcorpse.category.client_config")
                .push("ClientConfig");

        BLOOD_EFFECTS = BUILDER
                .comment("Blood..")
                .translation("config.sablemobragdollcorpse.blood")
                .define("blood effects", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
