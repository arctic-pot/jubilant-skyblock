package io.github.arcticpot.jublockly.mixin;

import io.github.arcticpot.jublockly.StatParserKt;
import io.github.arcticpot.jublockly.utils.SkyblockUtilsKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    @Shadow
    public void setOverlayMessage(Text message, boolean tinted) {}

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void $renderStatusBar(MatrixStack matrices, CallbackInfo ci) {
        if (!SkyblockUtilsKt.isSkyBlock()) return;
        ci.cancel();
    }

    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void $setOverlayMessage(Text message, boolean tinted, CallbackInfo ci) {
        if (!SkyblockUtilsKt.isSkyBlock()) return;
        final String restString = StatParserKt.getStatParser().parse(message.getString());
        if (restString.isEmpty()) ci.cancel();
        else setOverlayMessage(Text.of(restString), tinted);
    }
}