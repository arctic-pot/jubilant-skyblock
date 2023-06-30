package io.github.arcticpot.jublockly.utils.gui.text

import io.github.arcticpot.jublockly.utils.gui.HorizontalAlignment
import net.minecraft.client.MinecraftClient

/**
 * Provide a HORIZONTAL alignment for text
 */
class TextAlignmentBox(
    private var x: Int = 0,
    private var alignment: HorizontalAlignment = HorizontalAlignment.Left,
    private val padding: Int = 0,
    private val width: Int
) {
    private val textRenderer = MinecraftClient.getInstance().textRenderer

    fun moveTo(x: Int) {
        this.x = x
    }
    fun reAlign(alignment: HorizontalAlignment) {
        this.alignment = alignment
    }

    fun getAlignedX(text: String): Int {
        return x + alignment.getOffsetX(
            contentWidth = textRenderer.getWidth(text) + padding,
            boxWidth = width)
    }
    fun getRightX(text: String): Int {
        return getAlignedX(text) + textRenderer.getWidth(text)
    }
}