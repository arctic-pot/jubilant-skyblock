package io.github.arcticpot.jublockly.features.status

import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

/**
 * All the bar texture are in one file. This class specifies the position of the texture and provides utils.
 * @param barIndex The index of the bars, counting vertically from the top.
 */
class StatBarTexture(barIndex: Int) {
    private val v = barIndex * 5 + BAR_OFFSET

    companion object {
        // The offset to the bar texture, ignoring the empty texture.
        // For example, if the height of the empty bar texture is `h`, then the y of the texture of the bar whose
        // index=0 should be `h` instead of 0. That is, barOffset=`x`.
        // WTF DID I WRITE :skull:
        private const val BAR_OFFSET = 14

        val RESOURCE = Identifier("jublockly", "textures/gui/bars.png")

        fun drawBarBorderOf(context: DrawContext, x: Int, y: Int, width: Int) {
            context.drawTexture(RESOURCE, x - 1, y - 1, 0, 0, width, 7)
            context.drawTexture(RESOURCE, x + width - 1, y - 1, 183, 0, 2, 7)
        }

        fun drawBlinkingBarBorderOf(context: DrawContext, x: Int, y: Int, width: Int) {
            context.drawTexture(RESOURCE, x - 1, y - 1, 0, 7, width, 7)
            context.drawTexture(RESOURCE, x + width - 1, y - 1, 184, 7, 2, 7)
        }
    }

    fun draw(context: DrawContext, x: Int, y: Int, width: Int, includeRight: Boolean = true) {
        context.drawTexture(RESOURCE, x, y, 0, v, width - 1, 5)
        context.drawTexture(RESOURCE, x + width - 1, y, if (includeRight) 181 else 1, v, 1, 5)
    }

}