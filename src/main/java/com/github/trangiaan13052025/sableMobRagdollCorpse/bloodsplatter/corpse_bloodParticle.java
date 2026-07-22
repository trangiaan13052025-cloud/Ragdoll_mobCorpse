package com.github.trangiaan13052025.sableMobRagdollCorpse.bloodsplatter;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class corpse_bloodParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private float oldRed = 1.0f;
    private float oldQuadSize = 0.01f;

    protected corpse_bloodParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.spriteSet = spriteSet;
        this.friction = 0.9f;
        this.lifetime = 240;
        this.setSpriteFromAge(spriteSet);

        float red = 0.3f + this.random.nextFloat() * 0.7f;
        float green = 0.3f + this.random.nextFloat() * 0.7f;
        this.oldRed = red;
        this.rCol = red;
        this.gCol = 0.1f * green;
        this.bCol = 0f;
        this.alpha = 1f;

        this.gravity = 0.8f;
        this.oldQuadSize = 0.01f + this.random.nextFloat() * 0.03f;
        this.quadSize = this.oldQuadSize;
        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        if (this.onGround) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

            this.xd = 0;
            this.yd = 0;
            this.zd = 0;
        }

        super.tick();

        if (!this.removed) {
            this.setSpriteFromAge(this.spriteSet);
            float scale = Mth.clamp((float) this.age / (this.lifetime / 1.5f), 0.0f, 1.0f);
            float colorScale = (1.0f - scale) * 0.8f;
            colorScale = colorScale + 0.2f;
            this.rCol = this.oldRed * colorScale;
            this.quadSize = this.oldQuadSize + scale * 0.04f;
        } else {
            return;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double xSpd, double ySpd, double zSpd) {
            return new corpse_bloodParticle(clientLevel, x, y, z, this.spriteSet, xSpd, ySpd, zSpd);
        }
    }
}