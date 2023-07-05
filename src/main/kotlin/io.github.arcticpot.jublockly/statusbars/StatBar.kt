package io.github.arcticpot.jublockly.statusbars

import io.github.arcticpot.jublockly.utils.gui.HorizontalAlignment
import io.github.arcticpot.jublockly.utils.gui.text.TextUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

class StatBar(
    private var x: Int,
    private var y: Int,
    private var width: Int,
) {
    companion object {
        private val client = MinecraftClient.getInstance()
        private val textRenderer = client.textRenderer
    }

    private var maxValue: Int = 0

    fun drawBorder(context: DrawContext, blinking: Boolean = false) {
        if (blinking) StatBarTexture.drawBlinkingBarBorderOf(context, x, y, width)
        else StatBarTexture.drawBarBorderOf(context, x, y, width)
    }

    fun fillPercentage(context: DrawContext, texture: StatBarTexture, percentage: Float) {
        if (percentage <= 0) return
        val fillWidth = (width * percentage).toInt()
        if (fillWidth <= 0) return
        texture.draw(context, x, y, fillWidth, percentage >= 1f)
    }

    fun fillValue(context: DrawContext, texture: StatBarTexture, value: Int) {
        if (value <= 0) return
        fillPercentage(context, texture, value.toFloat() / maxValue.toFloat())
    }

    fun drawText(context: DrawContext, offset: Int = -4, text: String, color: Int) {
        val textWidth = textRenderer.getWidth(text)
        val textX = x + HorizontalAlignment.Center.getOffsetX(textWidth, width)
        val textY = y + offset
        TextUtils.drawBlackBorderedText(context, textRenderer, text, textX, textY, color)
    }

    fun setMaxValue(maxValue: Int) {
        this.maxValue = maxValue
    }

    fun move(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}