package io.github.arcticpot.jublockly.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.util.Formatting

enum class DungeonType {
    None,
    Catacombs
}

var isSkyBlock = false
var dungeon = DungeonType.None

fun getScoreboard() {
    val player = MinecraftClient.getInstance().player ?: return
    val scoreboard = player.scoreboard
    val objective = scoreboard.getObjectiveForSlot(0)
    val scores = scoreboard.getAllPlayerScores(objective)
    scores.removeIf{ score -> Formatting.strip(score.playerName)!!.isEmpty() }
}
