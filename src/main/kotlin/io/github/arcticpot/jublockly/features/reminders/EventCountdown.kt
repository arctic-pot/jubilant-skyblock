package io.github.arcticpot.jublockly.features.reminders

import io.github.arcticpot.jublockly.base.sbevents.SkyBlockEvent.Companion.sortedByNextTime
import io.github.arcticpot.jublockly.base.sbevents.SkyBlockEvents
import io.github.arcticpot.jublockly.features.AnimatedValue
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

object EventCountdown {
    val width = AnimatedValue(40)
    private val HEADS_RESOURCE = Identifier("jublockly", "textures/gui/heads.png")
    private val textRenderer: TextRenderer = MinecraftClient.getInstance().textRenderer

    fun render(context: DrawContext) {
        renderIntervalEvents(context)
    }

    private fun timeDisplay(duration: Duration): String {
        duration.toComponents { days, hours, minutes, seconds, _ ->
            if (duration > 1.days) return "%dd %02d:%02d:%02d".format(days, hours, minutes, seconds)
            if (duration > 1.hours) return "%d:%02d:%02d".format(hours, minutes, seconds)
            return "%02d:%02d".format(minutes, seconds)
        }
    }

    private fun renderIntervalEvents(context: DrawContext) {
        val scaledWidth = context.scaledWindowWidth
        val headX = scaledWidth - width.intValue + 4
        val textX = scaledWidth - width.intValue + 24
//        context.fill(scaledWidth - width.intValue, 64, scaledWidth, 0, 0x99212121.toInt())

        val farmingContest = SkyBlockEvents.FARMING_CONTEST
        context.drawTexture(HEADS_RESOURCE, headX, 4, 16, 0 ,16, 16)
        if (farmingContest.isActive)
            context.drawText(textRenderer, "ACTIVE " + timeDisplay(farmingContest.untilConcluding),
                textX, 8, 0xff55ff55.toInt(), true)
        else
            context.drawText(textRenderer, timeDisplay(farmingContest.untilNext),
                textX, 8, 0xfffafafa.toInt(), true)

        val darkAuction = SkyBlockEvents.DARK_AUCTION

        context.drawText(textRenderer, timeDisplay(darkAuction.untilNext),
            textX, 28, 0xfffafafa.toInt(), true)

        val another = SkyBlockEvents.YEARLY_EVENTS.sortedByNextTime()[0]
//        println(another)
        context.drawItem(Items.SNOWBALL.defaultStack, headX, 44)
        context.drawText(textRenderer, timeDisplay(another.untilNext),
            textX, 48, 0xfffafafa.toInt(), true)
    }
}