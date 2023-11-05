package io.github.arcticpot.jublockly

import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.CommandDispatcher
import io.github.arcticpot.jublockly.config.JublocklyConfig
import io.github.arcticpot.jublockly.features.reminders.EventCountdown
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

object JublocklyCommand {
    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        val command = dispatcher.register(
            literal("jublockly")
                .then(literal("config").executes {
                        it.source.client.setScreen(JublocklyConfig.gui())
                        return@executes SINGLE_SUCCESS
                    }
                )
                .then(
                    literal("test:1").executes {
                        EventCountdown.width.animate(100, 500)
                        return@executes SINGLE_SUCCESS
                    }
                )
                .then(
                    literal("test:2").executes {
                        EventCountdown.width.animate(500, 1000)
                        return@executes SINGLE_SUCCESS
                    }
                )
        )
        dispatcher.register(literal("jub").redirect(command))
    }

    private fun executeConfig() { }
}