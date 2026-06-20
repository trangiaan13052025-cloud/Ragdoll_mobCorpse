package com.github.trangiaan13052025.sableMobRagdollCorpse;

import dev.leo.sableplayerragdoll.api.RagdollAPI;
import dev.leo.sableplayerragdoll.mob.api.MobRagdollLaunchOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = "mob_ragdoll_corpse")
public class ragdollOnDeath {

    static int lifeTime = (5 * 60) * 20;

    @SubscribeEvent
    public static void onFatalDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) { return; }

        if (entity instanceof Mob mob) {
            if (mob.getData(EntityAttachments.is_ragdoll_corpse)) {
                return;
            }

            float damage = event.getOriginalDamage();
            if (damage < mob.getHealth()) {
                return;
            }
            event.setNewDamage(0.0F);
            try {
                DamageSource source = event.getSource();
                mob.setHealth(mob.getMaxHealth());
                mob.deathTime = 0;
                mob.setData(EntityAttachments.is_ragdoll_corpse, true);
                mob.setData(EntityAttachments.corpsetimer, lifeTime);
                mob.setNoAi(true);
                mob.setSilent(true);
                mob.setPersistenceRequired();
                ServerLevel level = (ServerLevel) mob.level();
                Vec3 curDelta = mob.getDeltaMovement();
                float baseForce = damage*10;
                Vec3 delta = new Vec3(curDelta.x * baseForce, curDelta.y * baseForce + (baseForce/100), curDelta.z * baseForce);
                System.out.println(delta);
                double spinFactor = baseForce;

                //EntityType<?> type = mob.getType();
//                if (source.getSourcePosition() != null) {
//                    Vec3 sourcePos = source.getSourcePosition();
//                    Vec3 launchDirection = mob.position().subtract(sourcePos).normalize();
//                    double force = source.is(net.minecraft.tags.DamageTypeTags.IS_EXPLOSION) ? 1.2 : 0.4;
//                    delta = delta.add(new Vec3(launchDirection.x * force, 0.35, launchDirection.z * force));
//                }
//                else if (source.getEntity() instanceof LivingEntity attacker) {
//                    // ⚔️ MELEE ATTACKS: If no exact position exists but an attacker does, fly away from attacker
//                    Vec3 launchDirection = mob.position().subtract(attacker.position()).normalize();
//                    delta = new Vec3(launchDirection.x * 0.4, 0.25, launchDirection.z * 0.4);
//                }
//                else {
//                    // 🍂 NATURAL DEATHS (Fall damage, Poison, Void, Suffocation)
//                    // Let it drop down naturally, but give it a tiny random twitch so it doesn't look stiff
//                    delta = new Vec3((Math.random() - 0.5) * 0.1, -0.1, (Math.random() - 0.5) * 0.1);
//                    spinFactor = 0.2; // Minor slump twist
//                }

                double spinX = (Math.random() - 0.5) * spinFactor;
                double spinY = (Math.random() - 0.5) * spinFactor;
                double spinZ = (Math.random() - 0.5) * spinFactor;
                Vec3 delta_angular = new Vec3(spinX, spinY, spinZ);
                RagdollAPI.launchMob(
                        level,
                        mob,
                        delta,
                        delta_angular,
                        MobRagdollLaunchOptions.builder().durationTicks(999999999).build()
                );
            } catch (Exception e) {
                return;
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SubscribeEvent
    public static void onMobTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();

        if (entity == null || entity.level().isClientSide()) {
            return;
        }

        if (entity instanceof Mob mob) {
            if (!mob.hasData(EntityAttachments.is_ragdoll_corpse) || !mob.hasData(EntityAttachments.corpsetimer)) {
                return;
            }
            // Check if this specific entity is currently an active corpse
            if (mob.getData(EntityAttachments.is_ragdoll_corpse)) {

                // Get the current tick count saved on this mob
                int currentTicks = mob.getData(EntityAttachments.corpsetimer);

                // 5 Minutes = 5 * 60 seconds * 20 ticks per second = 6,000 ticks
                if (currentTicks <= 0) {
                    mob.setHealth(0);
                    mob.deathTime = 20;
                } else {
                    // Increment the counter by 1 every game tick and save it back onto the mob
                    mob.setData(EntityAttachments.corpsetimer, currentTicks - 1);
                }
            }
        }
    }
}
