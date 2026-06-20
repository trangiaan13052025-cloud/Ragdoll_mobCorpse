package com.github.trangiaan13052025.sableMobRagdollCorpse;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import dev.leo.sableplayerragdoll.api.RagdollAPI;

@EventBusSubscriber
public class cookingHandler {

    @SubscribeEvent
    public static void EntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob ) {
            if (RagdollAPI.isMobRagdolled(mob)) {
                
            }
        }
    }
}
