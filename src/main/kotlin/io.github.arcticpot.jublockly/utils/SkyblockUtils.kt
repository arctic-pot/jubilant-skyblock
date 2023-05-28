package io.github.arcticpot.jublockly.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.util.Formatting
import java.util.*

enum class DungeonType {
    None,
    Catacombs
}

var isSkyBlock = true
var dungeon = DungeonType.None

fun checkSkyBlock() {
    isSkyBlock = false
    val player = MinecraftClient.getInstance().player ?: return
    val scoreboard = player.scoreboard
    val sidebarObjective = scoreboard.getObjectiveForSlot(0) ?: return
//    val lastLine = scoreboard.getAllPlayerScores(sidebarObjective).last()
    isSkyBlock = Formatting.strip(sidebarObjective.displayName.string)!!.lowercase(Locale.getDefault()).contains("skyblock")

}

//fun getScoreboard() {
//    val player = MinecraftClient.getInstance().player ?: return
//    val scoreboard = player.scoreboard
//    val objective = scoreboard.getObjectiveForSlot(0)
//    val scores = scoreboard.getAllPlayerScores(objective)
//    scores.removeIf{ score -> Formatting.strip(score.playerName)!!.isEmpty() }
//}
