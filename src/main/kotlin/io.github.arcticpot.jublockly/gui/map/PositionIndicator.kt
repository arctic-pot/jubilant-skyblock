package io.github.arcticpot.jublockly.gui.map

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.universal.UMatrixStack
import gg.essential.universal.wrappers.UPlayer
import net.minecraft.client.gui.DrawContext
import java.awt.Color

object PositionIndicator {
    private const val mapWidth = 128
    private var isMapOpen = false

    private var window: Window = Window(ElementaVersion.V2)
    private val box = UIBlock(color = Color((0x77000000).toInt(), true)).constrain {
        x = 2.pixels
        y = 2.pixels()
        width = 72.pixels
        height = 13.pixels
    } childOf window

    private val posTextContainer = UIContainer() constrain {
        height = 13.pixels
    } childOf box

    private val posText = UIText(
        "", false
    ) constrain {
        x = 2.pixels
        y = CenterConstraint()
    } childOf posTextContainer


    fun draw(context: DrawContext) {
        val posX = UPlayer.getPosX().toInt()
        val posY = UPlayer.getPosY().toInt()
        val posZ = UPlayer.getPosZ().toInt()
        posText.setText("$posX, $posY, $posZ")
        window.draw(UMatrixStack.UNIT)

        Window(ElementaVersion.V2).draw(UMatrixStack.Compat.get())

    }

    fun switchMap() {
        isMapOpen = if (isMapOpen) {
            box.animate {
                setHeightAnimation(Animations.OUT_EXP, 0.5f, 13.pixels)
                setWidthAnimation(Animations.OUT_EXP, 0.5f, 72.pixels)
            }
            false
        } else {
            box.animate {
                setHeightAnimation(Animations.OUT_EXP, 0.5f, (13 + mapWidth).pixels)
                setWidthAnimation(Animations.OUT_EXP, 0.5f, mapWidth.pixels)
            }
            true
        }
    }
}