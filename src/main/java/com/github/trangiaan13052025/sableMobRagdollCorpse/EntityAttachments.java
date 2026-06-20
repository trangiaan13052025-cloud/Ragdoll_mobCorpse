package com.github.trangiaan13052025.sableMobRagdollCorpse;

import com.mojang.serialization.Codec;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod("mob_ragdoll_corpse")

public class EntityAttachments {
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

    public EntityAttachments(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
