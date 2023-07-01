package io.github.arcticpot.jublockly.config

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import java.io.File

object JublocklyConfig : Vigilant(File("./config/jublockly.toml")) {
    @Property(
        type = PropertyType.SWITCH,
        name = "config.jublockly.gui.enableActionBar",
        description = "config.jublockly.gui.enableActionBar.description",
        category = "config.jublockly.gui"
    )
    var enableActionBar = true

    init {
        initialize()
    }
}