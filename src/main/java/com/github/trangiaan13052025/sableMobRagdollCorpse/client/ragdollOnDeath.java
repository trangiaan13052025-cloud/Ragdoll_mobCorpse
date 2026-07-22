package com.github.trangiaan13052025.sableMobRagdollCorpse.client;

import com.github.trangiaan13052025.sableMobRagdollCorpse.bloodsplatter.corpse_bloodParticle;
import com.github.trangiaan13052025.sableMobRagdollCorpse.particles.modParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod("mob_ragdoll_corpse")
public class ragdollOnDeath {
    @EventBusSubscriber(modid = "mob_ragdoll_corpse", value = Dist.CLIENT)
    public static class ClientModEvents {
        //@SubscribeEvent
        //public static void onClientSetup(FMLClientSetupEvent event) {

        //}

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(modParticles.corpse_blood_particle.get(), corpse_bloodParticle.Provider::new);
        }
    }
}
