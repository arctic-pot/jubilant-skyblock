package io.github.arcticpot.jublockly.client

import com.mojang.brigadier.CommandDispatcher
import io.github.arcticpot.jublockly.Jublockly
import io.github.arcticpot.jublockly.config.JublocklyConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.command.CommandRegistryAccess
import org.lwjgl.glfw.GLFW


class JublocklyClient : ClientModInitializer {
    companion object {
        private lateinit var configKey: KeyBinding
    }

    /**
     * Runs the mod initializer on the client environment.
     */
    override fun onInitializeClient() {
        configKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.jublockly.config",  // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM,  // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_UNKNOWN,  // The keycode of the key
                "category.jublockly.general" // The translation key of the keybinding's category.
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick {
            if (configKey.wasPressed()) {
                val gui = JublocklyConfig.gui()
                MinecraftClient.getInstance().setScreen(gui)
            }
        })


        ClientCommandRegistrationCallback.EVENT.register {
                dispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandRegistryAccess ->
            dispatcher.register(
                ClientCommandManager.literal("jublockly:config")
                .executes {
                    JublocklyConfig
                    Jublockly.logger.info("Opening CONFIG by command!")
                    return@executes 1
                })
        }
    }
}
