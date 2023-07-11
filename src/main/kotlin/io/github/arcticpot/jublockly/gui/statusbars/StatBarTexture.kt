package io.github.arcticpot.jublockly.gui.statusbars

import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

class StatBarTexture(barIndex: Int) {
    private val v = barIndex * 5 + barOffset

    companion object {
        // The offset to the bar texture, ignoring the empty texture.
        // For example, if the height of the empty bar texture is `x`, then the y of the texture of the bar whose
        // index=0 should be `x` instead of 0. That is, barOffset=`x`.
        private const val barOffset = 14

        val resource = Identifier("jublockly", "textures/gui/bars.png")

        fun drawBarBorderOf(context: DrawContext, x: Int, y: Int, width: Int) {
            context.drawTexture(resource, x - 1, y - 1, 0, 0, width, 7)
            context.drawTexture(resource, x + width - 1, y - 1, 183, 0, 2, 7)
        }

        fun drawBlinkingBarBorderOf(context: DrawContext, x: Int, y: Int, width: Int) {
            context.drawTexture(resource, x - 1, y - 1, 0, 7, width, 7)
            context.drawTexture(resource, x + width - 1, y - 1, 184, 7, 2, 7)
        }
    }

    fun draw(context: DrawContext, x: Int, y: Int, width: Int, includeRight: Boolean = true) {
        context.drawTexture(resource, x, y, 0, v, width - 1, 5)
        context.drawTexture(resource, x + width - 1, y, if (includeRight) 181 else 1, v, 1, 5)
    }
}