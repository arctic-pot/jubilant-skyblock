package io.github.arcticpot.jublockly.features.status

import io.github.arcticpot.jublockly.base.SkyBlockHelper.onSkyBlock
import io.github.arcticpot.jublockly.base.data.ActionBarData
//import io.github.arcticpot.jublockly.base.data.bars.StatBarLayer
import io.github.arcticpot.jublockly.config.JublocklyConfig.enableActionBar
import io.github.arcticpot.jublockly.utils.gui.text.TextUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

object FancyActionBar {
    private val client = MinecraftClient.getInstance()
    private val textRenderer = client.textRenderer
    private const val barHeight = 5
    private const val barWidth = 87
    private const val hotbarWidth = 182
    private val healthTexture = StatBarTexture(0)
    private val absorptionTexture = StatBarTexture(1)
    private val witherTexture = StatBarTexture(2)
    private val poisonTexture = StatBarTexture(3)
    private val manaTexture = StatBarTexture(4)
    private val overflowManaTexture = StatBarTexture(5)
    private val experienceTexture = StatBarTexture(6)
    private val healthBar = StatBar(0, 0, barWidth)
    private val manaBar = StatBar(0, 0, barWidth)
    private val experienceBar = StatBar(0, 0, hotbarWidth)
    private val STATUS_RESOURCE = Identifier("jublockly", "textures/gui/status.png")

//    private val healthBars = io.github.arcticpot.jublockly.base.data.bars.StatBar(arrayOf(
//            StatBarLayer(0xffffe066, 1, 1)
//    ))

    fun render(context: DrawContext, healthBarBlinking: Boolean): Boolean {
        if (!onSkyBlock || !enableActionBar) return false
        val scaledWidth = context.scaledWindowWidth
        val scaledHeight = context.scaledWindowHeight
        val hotbarX: Int = (scaledWidth - hotbarWidth) / 2
        val hotbarY: Int = scaledHeight - 22
        val xpBarY: Int = hotbarY - 2 - barHeight
        val statBarY: Int = xpBarY - 6 - barHeight

//        if (false && ActionBarData.riftTime.presenting) {
//
//            return true
//        }
        drawHealthBar(context, hotbarX, statBarY, healthBarBlinking)
        drawManaBar(context, hotbarX + hotbarWidth - barWidth, statBarY)
        drawExperienceBar(context, hotbarX, xpBarY)

        if (ActionBarData.zombieSwordAvailable.presenting) {
            drawZombieSword(context, hotbarX + hotbarWidth + 6, hotbarY - textRenderer.fontHeight - 3)
        }

        // Draw Defense

        val defense = ActionBarData.trueDefense.valueNullable ?: ActionBarData.defense.get()
        val eHealth = (ActionBarData.maxHealth.get() * (100 + ActionBarData.defense.get()) / 100).toUInt()
        val defenseX = hotbarX + hotbarWidth + 6
        val defenseY = scaledHeight - 2 * textRenderer.fontHeight - 4
        val effectiveHealthX = hotbarX + hotbarWidth + 6
        val effectiveHealthY = scaledHeight - textRenderer.fontHeight - 2
        TextUtils.drawBlackBorderedText(context, textRenderer, defense.toString(), defenseX, defenseY,
            (if (ActionBarData.trueDefense.presenting) 0xffffffff else 0xff69db7c).toInt())
        TextUtils.drawBlackBorderedText(context, textRenderer, eHealth.toString(), effectiveHealthX, effectiveHealthY,
            (if (ActionBarData.trueDefense.presenting) 0xffaab0a7 else 0xff12b886).toInt())


        return true
    }


    private fun drawHealthBar(context: DrawContext, x: Int, y: Int, blinking: Boolean) {
        val health = ActionBarData.health
        val maxHealth = ActionBarData.maxHealth
        val barText = "${health.get()}/${maxHealth.get()}"
        with(healthBar) {
            move(x, y)
            drawBorder(context, blinking)
            fillStat(context, healthTexture, ActionBarData.healthBound)
            fillStat(context, absorptionTexture, ActionBarData.absorptionBound)
            drawText(context, -4, barText, (if (health.get() > maxHealth.get()) 0xffffe066 else 0xffec3500).toInt())
        }
    }


    private fun drawManaBar(context: DrawContext, x: Int, y: Int) {
        val mana = ActionBarData.mana
        val overflowMana = ActionBarData.overflowMana
        val intelligence = ActionBarData.maxMana
        with(manaBar) {
            move(x, y)
            drawBorder(context)
//            fillStat(context, overflowManaTexture, ActionBarData.overflowManaBound)
            fillStat(context, manaTexture, ActionBarData.manaBound)
            drawText(context, -4, "${mana.get()}/${intelligence.get()}", (0xff66d9e8).toInt())
        }

        if (overflowMana.presenting) {
            // Draw overflow mana text
            val barText2 = "${overflowMana.value}ʬ"
            val x2 = x + barWidth - 7
            val y2 = y - 5
            TextUtils.drawBlackBorderedText(context, textRenderer, barText2, x2, y2, (0xffb197fc).toInt())
        }
    }

    private fun drawExperienceBar(context: DrawContext, x: Int, y: Int) {
        val player = client.player!!
        with(experienceBar) {
            move(x, y)
            drawBorder(context)
            fillPercentage(context, experienceTexture, player.experienceProgress)
            if (player.experienceLevel > 0) {
                drawText(context, -4, client.player!!.experienceLevel.toString(), 8453920)
            }
        }
    }

    private fun drawZombieSword(context: DrawContext, x: Int, y: Int) {
        val available = ActionBarData.zombieSwordAvailable.get()
        val used = ActionBarData.zombieSwordUsed.get()
        var currentX = x
        repeat(available) {
            context.drawTexture(STATUS_RESOURCE, currentX, y, 0, 0, 7, 7)
            currentX += 8
        }
        repeat(used) {
            context.drawTexture(STATUS_RESOURCE, currentX, y, 8, 0, 7, 7)
            currentX += 8
        }
    }




//    private class StatBar(textColor: Int) {
//        private val emptyTexture = StatBarText e(column = 0)
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