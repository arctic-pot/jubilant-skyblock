package io.github.arcticpot.jublockly

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import kotlin.math.ceil

class StatBars : DrawableHelper() {
    private val textRenderer: TextRenderer = MinecraftClient.getInstance().textRenderer
    private val barResource = Identifier("jublockly", "textures/gui/bars.png")
    private val barHeight = 5
    private val emptyTexture = StatBarTexture(column = 0)
    private val healthTexture = StatBarTexture(column = 1)
    private val absorptionTexture = StatBarTexture(column = 2)
    private val witherTexture = StatBarTexture(column = 3)
    private val poisonTexture = StatBarTexture(column = 4)
    private val manaTexture = StatBarTexture(column = 5)
    private val overflowManaTexture = StatBarTexture(column = 6)
    private val actionbarParser = ActionbarParser.instance
    private val textMatrices = MatrixStack()
    init {
        textMatrices.scale(0.5f, 0.5f, 0.5f)
    }

    fun draw(matrices: MatrixStack, scaledWidth: Int, scaledHeight: Int) {
        val hotbarWidth = 182
        val hotbarX: Int = (scaledWidth - hotbarWidth) / 2
        val hotbarY: Int = scaledHeight - 22
        val statBarY: Int = hotbarY - 2 - barHeight

        val mana = actionbarParser.mana
        RenderSystem.setShaderTexture(0, barResource)
        drawHealthBar(matrices, hotbarX, statBarY)
        RenderSystem.setShaderTexture(0, barResource)
        drawManaBar(matrices, hotbarX + hotbarWidth - 82, statBarY)
//        RenderSystem.setShaderTexture(0, barResource)
//        manaTexture.drawLeft(matrices, hotbarX + hotbarWidth - 82, statBarY)
//        manaTexture.drawBar(matrices, hotbarX + hotbarWidth - 82 + 1, statBarY,
//            (1 * 80f).toInt())
//        manaTexture.drawRight(matrices, hotbarX + hotbarWidth - 82 + 81, statBarY)
//        println(mana)
    }



    private fun drawHealthBar(matrices: MatrixStack, x: Int, y: Int) {
        val health = actionbarParser.health
        emptyTexture.drawWhole(matrices, x, y, 82)
        if (health.value > 0) {
            healthTexture.drawWhole(matrices, x, y,
                (health.value.toFloat() / health.max.toFloat() * 82f).toInt(),
                drawRight = health.value >= health.max)
        }
        if (health.overflow > 0) {
            absorptionTexture.drawWhole(matrices, x, y,
                ceil(health.overflow.toFloat() / health.max.toFloat() * 82f).toInt(),
                drawRight = false)
        }

        val healthBarText = "${health.value + health.overflow}/${health.max}"

        drawOutlinedText(matrices, healthBarText,
            x + 3f, y - 8f,
            color = (0xffe03131).toInt(), outlineColor = (0xff240707).toInt())
    }


    private fun drawManaBar(matrices: MatrixStack, x: Int, y: Int) {
        val mana = actionbarParser.mana
        emptyTexture.drawWhole(matrices, x, y, 82)
        if (mana.value > 0) {
            manaTexture.drawWhole(matrices, x, y,
                (mana.value.toFloat() / mana.max.toFloat() * 82f).toInt(),
                drawRight = mana.value >= mana.max)
        }

        val healthBarText = "${mana.value + mana.overflow}/${mana.max}"
        drawOutlinedText(matrices, healthBarText,
            x + 3f, y - 8f,
            color = (0xff66d9e8).toInt(), outlineColor = (0xff1e4045).toInt())
    }

    private class StatBarTexture(column: Int) {
        private val v = column * 5
        fun drawLeft(matrices: MatrixStack, x: Int, y: Int) {
            drawTexture(matrices, x, y, 0, v, 1, 5)
        }
        fun drawBar(matrices: MatrixStack, x: Int, y: Int, width: Int) {
            assert(width <= 100)
            drawTexture(matrices, x, y, 1, v, width, 5)
        }
        fun drawRight(matrices: MatrixStack, x: Int, y: Int) {
            drawTexture(matrices, x, y, 91, v, 1, 5)
        }

        fun drawWhole(matrices: MatrixStack, x: Int, y: Int, width: Int, drawRight: Boolean = true) {
            drawLeft(matrices, x, y)
            drawBar(matrices, x + 1, y, width - 2)
            if (drawRight) drawRight(matrices, x + width - 1, y)
        }
    }

    private fun drawOutlinedText(matrices: MatrixStack, text: String, x: Float, y: Float, color: Int, outlineColor: Int) {
        textRenderer.draw(matrices, text, x + 1f, y, outlineColor)
        textRenderer.draw(matrices, text, x - 1f, y, outlineColor)
        textRenderer.draw(matrices, text, x, y + 1f, outlineColor)
        textRenderer.draw(matrices, text, x, y - 1f, outlineColor)
        textRenderer.draw(matrices, text, x, y, color)
    }

//    private class StatBar(textColor: Int) {
//        private val emptyTexture = StatBarTexture(column = 0)
//        private val barHeight = 5
//        fun draw(
//            matrices: MatrixStack,
//            texture: StatBarTexture,
//            overflowTexture: StatBarTexture?,
//            value: Int,
//            max: Int,
//            overflow: Int?,
//            x: Int,
//            y: Int,
//            width: Int,
//            leftText: String? = null,
//            rightText: String? = null
//        ) {
//            if (value < max) {
//                // Draws the empty bar texture
//                drawBarTexture(matrices, x, y, width, emptyTexture)
//            }
//            drawBarTexture(matrices, x, y, (value / max) * (width - 2), texture, drawRightSide = false)
//            if (overflow != null && overflow > 0) {
//                drawBarTexture(matrices, x, y, (overflow / max) * (width - 2), overflowTexture!!, drawRightSide = false)
//            }
//        }

//        private fun drawBarTexture(matrices: MatrixStack, x: Int, y: Int, width: Int, texture: StatBarTexture, drawRightSide: Boolean = true) {
//            drawTexture(matrices, x, y, texture.uLeft.toFloat(),
//                emptyTexture.v.toFloat(), 1, barHeight, 1, barHeight)
//            drawTexture(matrices, x + 1, y, texture.uBar.toFloat(),
//                emptyTexture.v.toFloat(), 1, barHeight, width, barHeight)
//            if (drawRightSide) drawTexture(matrices, x + width - 1, y, texture.uRight.toFloat(),
//                emptyTexture.v.toFloat(), 1, barHeight, 1, barHeight)
//        }
//    }
}