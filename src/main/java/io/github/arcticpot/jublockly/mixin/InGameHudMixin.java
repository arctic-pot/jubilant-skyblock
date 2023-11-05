package io.github.arcticpot.jublockly.mixin;

import io.github.arcticpot.jublockly.base.data.ActionBarData;
import io.github.arcticpot.jublockly.config.JublocklyConfig;
import io.github.arcticpot.jublockly.features.reminders.EventCountdown;
import io.github.arcticpot.jublockly.features.status.FancyStatusBar;
import io.github.arcticpot.jublockly.base.SkyBlockHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    public void setOverlayMessage(Text message, boolean tinted) {}

    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;

    @Shadow private @Nullable Text overlayMessage;

    @Shadow private int overlayRemaining;

    @Shadow private boolean overlayTinted;

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void $renderStatusBar(DrawContext context, CallbackInfo ci) {
        final boolean healthBarBlinking = false;
        if (FancyStatusBar.INSTANCE.render(context, healthBarBlinking))
            ci.cancel();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", ordinal = 1))
    private void renderOther(DrawContext context, float tickDelta, CallbackInfo ci) {
        EventCountdown.INSTANCE.render(context);
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void $renderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        if (SkyBlockHelper.INSTANCE.getOnSkyBlock() && JublocklyConfig.INSTANCE.getEnableActionBar())
            ci.cancel();
    }

    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void $setOverlayMessage(Text message, boolean tinted, CallbackInfo ci) {
        if (!SkyBlockHelper.INSTANCE.getOnSkyBlock() || !JublocklyConfig.INSTANCE.getEnableActionBar()) return;
        final Text rest = ActionBarData.INSTANCE.onModifyGame(message, true);
        if (!rest.getString().isEmpty()) {
            if (rest.getString().equals(message.getString())) {
                return;
            } else {
                this.overlayMessage = rest;
                this.overlayRemaining = 60;
                this.overlayTinted = tinted;
            }
        }
        ci.cancel();
    }
}