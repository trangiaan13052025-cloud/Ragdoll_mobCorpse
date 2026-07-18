package com.github.trangiaan13052025.sableMobRagdollCorpse.client;

import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClothScreenHelper {
    public static void register(ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (containerInstance, parentScreen) -> configGuiBuilder.createScreen(parentScreen)
        );
    }
}

