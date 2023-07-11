package io.github.arcticpot.jublockly

import com.mojang.brigadier.CommandDispatcher
import io.github.arcticpot.jublockly.config.JublocklyConfig
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

object JublocklyCommand {
    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        val command = dispatcher.register(
            ClientCommandManager.literal("jublockly").then(
                ClientCommandManager.literal("config").executes {
                    executeConfig()
                    return@executes 1
                }
            )
        )
        dispatcher.register(ClientCommandManager.literal("jub").redirect(command))
    }

    private fun executeConfig() = JublocklyConfig.showScreen()
}