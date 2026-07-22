package com.github.trangiaan13052025.sableMobRagdollCorpse;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class entityAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "mob_ragdoll_corpse");

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> is_ragdoll_corpse =
            ATTACHMENT_TYPES.register("is_ragdoll_corpse", () ->
                    AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> corpsetimer =
            ATTACHMENT_TYPES.register("corpsetimer", () ->
                    AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> persistent_ragdoll =
            ATTACHMENT_TYPES.register("persistent_ragdoll", () ->
                    AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> bleeding_corpse =
            ATTACHMENT_TYPES.register("bleeding_corpse", () ->
                    AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .sync(ByteBufCodecs.BOOL)
                            .build()
            );

    public entityAttachments(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
