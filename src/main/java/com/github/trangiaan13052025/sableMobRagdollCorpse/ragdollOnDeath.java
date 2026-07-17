package com.github.trangiaan13052025.sableMobRagdollCorpse;

import com.github.trangiaan13052025.sableMobRagdollCorpse.configs.serverConfig;
import com.github.trangiaan13052025.sableMobRagdollCorpse.data.ignoredEntities;
import com.github.trangiaan13052025.sableMobRagdollCorpse.data.shortLivedEntities;
import com.github.trangiaan13052025.sableMobRagdollCorpse.utils.mobUtils;
import dev.leo.sableplayerragdoll.api.RagdollAPI;
import dev.leo.sableplayerragdoll.mob.api.MobRagdollLaunchOptions;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@Mod("mob_ragdoll_corpse")
@EventBusSubscriber(modid = "mob_ragdoll_corpse")
public class ragdollOnDeath {

    static int lifeTime = (5 * 60) * 20;

    public ragdollOnDeath(IEventBus modEventBus, ModContainer container) {
        EntityAttachments.ATTACHMENT_TYPES.register(modEventBus);
        container.registerConfig(ModConfig.Type.COMMON, serverConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    //bublububulublulbu:3
    @SubscribeEvent
    public static void onFatalDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) { return; }

        if (entity instanceof Mob mob) {

            if (mob.getData(EntityAttachments.is_ragdoll_corpse)) {
                return;
            }

            if (ignoredEntities.shouldIgnoreEntity(entity)) {
                return;
            }

            float damage = event.getOriginalDamage();
            if (damage < mob.getHealth()) {
                return;
            }

            int deathTimeOverride = -1;
            Vec3 hitPos = new Vec3(0, 0, 0);
            Vec3 hitForce = new Vec3(0, 0, 0);

            DamageSource source = event.getSource();
            // Checky check stuff to avoid too many corpses

            Vec3 CoM = mob.getPosition(0.5F).add(0, mob.getBbHeight() / 2.0f, 0);

            if (source.getDirectEntity() != null) {
                hitPos = source.getDirectEntity().position().subtract(CoM);
                hitForce = source.getDirectEntity().getDeltaMovement();
            }

            if (source.getEntity() instanceof Player player) {
                if (source.isDirect()) {
                    float dmg = event.getOriginalDamage();
                    Vec3 lookVec = player.getLookAngle();
                    Vec3 plrCoM = player.getPosition(0.5f).add(0, player.getBbHeight() / 2.0f, 0);

                    float punchStrength = 2.0f;
                    float enchanted = 0.0f;

                    ItemStack mainhand = player.getMainHandItem();
                    var enchantmentRegistry = player.level().registryAccess()
                            .registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);

                    Holder<Enchantment> knockbackHolder = enchantmentRegistry.getHolderOrThrow(net.minecraft.world.item.enchantment.Enchantments.KNOCKBACK);
                    Holder<Enchantment> sharpnessHolder = enchantmentRegistry.getHolderOrThrow(net.minecraft.world.item.enchantment.Enchantments.SHARPNESS);

                    if (!mainhand.isEmpty()) {
                        float knockback = mainhand.getEnchantmentLevel(knockbackHolder);
                        float sharpness = mainhand.getEnchantmentLevel(sharpnessHolder);
                        enchanted = knockback - (sharpness / 5.0f);
                    }

                    float power = 0.1f * dmg * Mth.clamp(punchStrength + enchanted, 0.0f, 999.0f);
                    float offsetMul = 0.3f;
                    hitPos = plrCoM.subtract(CoM).multiply(offsetMul, offsetMul, offsetMul);
                    hitForce = lookVec.multiply(power, power, power);
                }
            } else {
                if (source.is(DamageTypeTags.IS_PROJECTILE)) {
                    deathTimeOverride = lifeTime / 5;
                }
            }

            event.setNewDamage(0f);
            try {
                int halfHealth = (int) mob.getMaxHealth() / 2;
                boolean shortLived = shortLivedEntities.entityCorpseLife_Short(mob);
                float damageLeftover = Mth.clamp(event.getOriginalDamage() - mob.getMaxHealth(), 0.0f, Float.POSITIVE_INFINITY);
                float corpseHealth = halfHealth - damageLeftover;
                if (corpseHealth <= 0.0f) {
                    event.setNewDamage(event.getOriginalDamage());
                    return;
                }
                corpseHealth = 2.0f;

                boolean isPersistentRagdoll = mobUtils.isTamed(mob);

                mob.setHealth(corpseHealth);
                mob.deathTime = 0;
                mob.setData(EntityAttachments.is_ragdoll_corpse, true);
                int corpseTimer = (deathTimeOverride > 0) ? deathTimeOverride : lifeTime;
                mob.setData(EntityAttachments.corpsetimer, shortLived ? (corpseTimer / 2) : corpseTimer);
                mob.setData(EntityAttachments.persistent_ragdoll, isPersistentRagdoll);
                mob.setNoAi(true);
                mob.setSilent(true);
                mob.setPersistenceRequired();
                ServerLevel level = (ServerLevel) mob.level();
                Vec3 curDelta = mob.getDeltaMovement();
//                float baseForce = damage*10;
//                Vec3 delta = new Vec3(curDelta.x * baseForce, curDelta.y * baseForce + (baseForce/100), curDelta.z * baseForce);
//                double spinFactor = baseForce;
//
//                double spinX = (Math.random() - 0.5) * spinFactor;
//                double spinY = (Math.random() - 0.5) * spinFactor;
//                double spinZ = (Math.random() - 0.5) * spinFactor;
//                Vec3 delta_angular = new Vec3(spinX, spinY, spinZ);
                Double hitPower = hitForce.length();
                Vec3 delta = curDelta.add(hitPos.multiply(hitForce.x, hitForce.y, hitForce.z));

                float BbWidth = mob.getBbWidth();
                float BbHeight = mob.getBbHeight();
                float mass = BbWidth * BbWidth * BbHeight * 0.1F;

                double torqueX = (hitPos.y * hitForce.z) - (hitPos.z * hitForce.y);
                double torqueY = (hitPos.z * hitForce.x) - (hitPos.x * hitForce.z);
                double torqueZ = (hitPos.x * hitForce.y) - (hitPos.y * hitForce.x);

                Vec3 torque = new Vec3(torqueX, torqueY, torqueZ);

                float momentOfInertia = mass * 0.4F;
                Vec3 delta_angular = torque.scale(1.0F / momentOfInertia);
                if (RagdollAPI.isMobRagdolled(mob)) {
                    RagdollAPI.releaseMob(mob);
                }

                RagdollAPI.launchMob(
                        level,
                        mob,
                        delta,
                        delta_angular,
                        MobRagdollLaunchOptions.builder().durationTicks(Integer.MAX_VALUE).build()
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
            if (mob instanceof TamableAnimal tamable) {
                if (tamable.isTame()) {
                    return;
                }
            }

            if (!mob.hasData(EntityAttachments.is_ragdoll_corpse) || !mob.hasData(EntityAttachments.corpsetimer)) {
                return;
            }
            // Check if this specific entity is currently an active corpse
            if (mob.getData(EntityAttachments.is_ragdoll_corpse)) {

                // Get the current tick count saved on this bullshit
                int currentTicks = mob.getData(EntityAttachments.corpsetimer);

                // 5 Minutes = 5 * 60 seconds * 20 ticks per second = 6,000 ticks
                if (currentTicks <= 0) {
                    if (mob.deathTime < 15) {
                        mob.setHealth(0);
                        mob.deathTime = 18;
                    }
                } else {
                    // crank ye counter by 1/tick and save it
                    if (!mob.getData(EntityAttachments.persistent_ragdoll)) {
                        mob.setData(EntityAttachments.corpsetimer, currentTicks - 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            return;
        }
        if (entity instanceof Mob mob) {
            ServerLevel level = (ServerLevel) mob.level();
            if (mob.getData(EntityAttachments.is_ragdoll_corpse)) {
                if (!RagdollAPI.isMobRagdolled(mob)) {
                    RagdollAPI.launchMob(
                            level,
                            mob,
                            new Vec3(0, 0, 0),
                            new Vec3(0, 0, 0),
                            MobRagdollLaunchOptions.builder().durationTicks(Integer.MAX_VALUE).build()
                    );
                }
            }
        }
    }

}
