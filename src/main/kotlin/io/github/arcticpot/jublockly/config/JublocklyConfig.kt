package io.github.arcticpot.jublockly.config

import gg.essential.elementa.WindowScreen
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import net.minecraft.client.MinecraftClient
import java.io.File

object JublocklyConfig : Vigilant(File("./config/jublockly.toml")) {

    @Property(
        type = PropertyType.SWITCH,
        name = "config.jublockly.qol.enableFullBright",
        category = "config.jublockly.qol"
    )
    var enableFullBright = true

    @Property(
        type = PropertyType.SELECTOR,
        name = "config.jublockly.qol.fullBrightImplementation",
        category = "config.jublockly.qol",
        options = ["effect.minecraft.night_vision", "Gamma"]
    )
    var fullBrightImplementation = 0

    @Property(
        type = PropertyType.SWITCH,
        name = "config.jublockly.gui.enableActionBar",
        description = "config.jublockly.gui.enableActionBar.description",
        category = "config.jublockly.gui"
    )
    var enableActionBar = true

    @Property(
        type = PropertyType.SWITCH,
        name = "config.jublockly.gui.parseSecrets",
        description = "config.jublockly.gui.parseSecrets.description",
        category = "config.jublockly.gui"
    )
    var parseSecrets = true


    var gui: WindowScreen?
    init {
        initialize()

        gui = gui()

        val clazz = javaClass
        addDependency(clazz.getDeclaredField("fullBrightImplementation"), clazz.getDeclaredField("enableFullBright"))
        addDependency(clazz.getDeclaredField("parseSecrets"), clazz.getDeclaredField("enableActionBar"))
    }

    fun showScreen() {
        MinecraftClient.getInstance().setScreen(gui)
    }
}