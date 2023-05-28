package io.github.arcticpot.jublockly.mixin;

import io.github.arcticpot.jublockly.ActionbarParser;
import io.github.arcticpot.jublockly.StatBars;
import io.github.arcticpot.jublockly.utils.SkyblockUtilsKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    final private StatBars statBars = new StatBars();

    @Shadow
    public void setOverlayMessage(Text message, boolean tinted) {}

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void $renderStatusBar(MatrixStack matrices, CallbackInfo ci) {
        if (!SkyblockUtilsKt.isSkyBlock()) return;
        statBars.draw(matrices, this.scaledWidth, this.scaledHeight);
        ci.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void $renderExperienceBar(MatrixStack matrices, int x, CallbackInfo ci) {
        if (SkyblockUtilsKt.isSkyBlock()) ci.cancel();
    }

    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void $setOverlayMessage(Text message, boolean tinted, CallbackInfo ci) {
        if (!SkyblockUtilsKt.isSkyBlock()) return;
        final String restString = ActionbarParser.Companion.getInstance().parse(message.getString());
        if (restString.isEmpty()) ci.cancel();
        else setOverlayMessage(Text.of(restString), tinted);
    }
}