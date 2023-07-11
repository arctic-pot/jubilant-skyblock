package io.github.arcticpot.jublockly.utils.gui.text

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext

object TextUtils {
    fun drawBlackBorderedText(context: DrawContext, r: TextRenderer, text: String, x: Int, y: Int, color: Int) {
        context.drawText(r, text, x + 1, y, 0, false)
        context.drawText(r, text, x - 1, y, 0, false)
        context.drawText(r, text, x, y + 1, 0, false)
        context.drawText(r, text, x, y - 1, 0, false)
        context.drawText(r, text, x, y, color, false)
    }
}