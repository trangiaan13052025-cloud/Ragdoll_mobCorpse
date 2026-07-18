package com.github.trangiaan13052025.sableMobRagdollCorpse.client;

import com.github.trangiaan13052025.sableMobRagdollCorpse.configs.serverConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class configGuiBuilder {

    public static Screen createScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Component.literal("Sable Mob Ragdoll Corpse Config"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory server_category = builder.getOrCreateCategory(Component.literal("Server Settings"));

        server_category.addEntry(entryBuilder.startStrList(
                        Component.literal("Ignored Entity IDs"),
                        new ArrayList<>(serverConfig.IGNORED_ENTITY_IDS.get()) // Create mutable copy????
                )
                .setDefaultValue(List.of("minecraft:"))
                .setTooltip(Component.literal("A list of mob ids to ignore from creating corpse in case it is causing a bug"))

                .setSaveConsumer(updatedList -> {
                    serverConfig.IGNORED_ENTITY_IDS.set(updatedList);
                    serverConfig.SPEC.save();

                })
                .build());

        return builder.build();
    }
}

