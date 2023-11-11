package io.github.arcticpot.jublockly.mixin;

import io.github.arcticpot.jublockly.config.JublocklyConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    // Code reference: NightVision, MIT License
    // https://github.com/OffKilterMC/NightVision/blob/1.0.2/src/main/java/offkilter/nightvision/mixin/LightTextureMixin.java
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z "))
    private boolean $hasStatusEffect(ClientPlayerEntity instance, StatusEffect statusEffect) {
        if (JublocklyConfig.INSTANCE.getEnableFullBright()
                && JublocklyConfig.INSTANCE.getFullBrightImplementation() == 0
                && statusEffect == StatusEffects.NIGHT_VISION) {
            return true;
        } else {
            return instance.hasStatusEffect(statusEffect);
        }
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F"))
    private float $getNightVisionStrength(LivingEntity entity, float tickDelta)
    {
        if (JublocklyConfig.INSTANCE.getEnableFullBright()
                && JublocklyConfig.INSTANCE.getFullBrightImplementation() == 0) {
            return 1.0f;
        } else {
            return GameRenderer.getNightVisionStrength(entity, tickDelta);
        }
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float $floatValue(Double instance)
    {
        if (JublocklyConfig.INSTANCE.getEnableFullBright()
                && JublocklyConfig.INSTANCE.getFullBrightImplementation() == 1) {
            return 100f;
        } else {
            return instance.floatValue();
        }
    }
}
