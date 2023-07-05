package io.github.arcticpot.jublockly.mixin;

import io.github.arcticpot.jublockly.config.JublocklyConfig;
import io.github.arcticpot.jublockly.base.ActionBarParser;
import io.github.arcticpot.jublockly.gui.statusbars.FancyActionBar;
import io.github.arcticpot.jublockly.base.SkyblockHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
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

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void $renderStatusBar(DrawContext context, CallbackInfo ci) {
        if (!SkyblockHelper.INSTANCE.getOnSkyblock() || !JublocklyConfig.INSTANCE.getEnableActionBar())
            return;
        // :)
        final boolean healthBarBlinking = false;
        FancyActionBar.INSTANCE.draw(context, this.scaledWidth, this.scaledHeight, healthBarBlinking);
        ci.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void $renderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        if (SkyblockHelper.INSTANCE.getOnSkyblock() && JublocklyConfig.INSTANCE.getEnableActionBar())
            ci.cancel();
    }

    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void $setOverlayMessage(Text message, boolean tinted, CallbackInfo ci) {
        if (!SkyblockHelper.INSTANCE.getOnSkyblock() || !JublocklyConfig.INSTANCE.getEnableActionBar()) return;
        final String restString = ActionBarParser.INSTANCE.parse(message.getString());
        if (!restString.isEmpty()) {
            if (restString.equals(message.getString())) {
                return;
            } else {
                setOverlayMessage(Text.of(restString), tinted);
            }
        }
        ci.cancel();
    }
}