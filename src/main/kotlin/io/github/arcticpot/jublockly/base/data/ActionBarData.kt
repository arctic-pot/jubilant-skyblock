package io.github.arcticpot.jublockly.base.data

import net.minecraft.text.Text


object ActionBarData {
    val health = StatValue(0)
    val maxHealth = StatValue(0)
    val trueDefense = StatValue(0)
    val defense = StatValue(0)
    val mana = StatValue(0)
    val overflowMana = StatValue(0)
    val maxMana = StatValue(0)
    val zombieSwordAvailable = StatValue(0)
    val zombieSwordUsed = StatValue(0)
    val riftTimeMinutes = StatValue(0)
    val riftTimeSeconds = StatValue(0)
    val riftTime = StatValueCalculation(riftTimeSeconds, riftTimeMinutes) {
        (riftTimeMinutes.valueNullable ?: 0) * 60 + (riftTimeSeconds.valueNullable ?: 0)
    }

    val healthBound = BoundStat.limit(health, maxHealth)
    val absorptionBound = BoundStat.overflow(health, maxHealth)
    val manaBound = BoundStat(mana, maxMana)
    val overflowManaBound = BoundStat(overflowMana, maxMana)

    private class ActionBarMatch private constructor(matchResult: MatchResult?) {
        private val groups = matchResult?.groups
        private val range = matchResult?.range

        companion object {
            fun of(regex: Regex, string: String): ActionBarMatch {
                return ActionBarMatch(regex.find(string))
            }
        }

        fun valueAt(groupIndex: Int): String? {
            val group = groups?.get(groupIndex) ?: return null
            return group.value
        }

        fun intAt(groupIndex: Int): Int? {
            val groupValue = valueAt(groupIndex) ?: return null
            return parseInt(groupValue)
        }

        fun lengthAt(groupIndex: Int): Int? {
            val groupValue = valueAt(groupIndex) ?: return null
            return groupValue.length
        }

        fun exist(groupIndex: Int = 0): Boolean {
            if (groups == null) return false
            return groups[groupIndex] != null
        }

        fun strip(string: String): String {
            if (range == null) return string
            return string.removeRange(range)
        }
    }

    private fun parseInt(string: String) = string.replace(",", "").toInt()

    private val HEALTH_REGEX = Regex("§[6c](\\d{0,3}(,?\\d{3})*)/(\\d{0,3}(,?\\d{3})*)❤(\\+§c\\d{1,3}.)? *")
    private const val HEALTH_GROUP = 1
    private const val MAX_HEALTH_GROUP = 3
    private val DEFENSE_REGEX = Regex("§a(\\d{0,3}(,?\\d{3})*)§a❈ Defense *")
    private const val DEFENSE_GROUP = 1
    private val TRUE_DEFENSE_REGEX = Regex("§a(\\d{0,3}(,?\\d{3})*)§f❈ True Defense *")
    private const val TRUE_DEFENSE_GROUP = 1
    private val MANA_REGEX = Regex("§b(\\d{0,3}(,?\\d{3})*)/(\\d{0,3}(,?\\d{3})*)✎ (§3(\\d{0,3}(,?\\d{3})*)ʬ)|(Mana) *")
    private const val MANA_GROUP = 1
    private const val INTELLIGENCE_GROUP = 3
    private const val OVERFLOW_MANA_GROUP = 6
    private val ZOMBIE_SWORD_REGEX = Regex("§e§l(ⓩ{0,5})§6§l(Ⓞ{0,5}) *")
    private val RIFT_TIME_REGEX = Regex("((\\d+)m)?(\\d{1,2})sф Left *")
    private const val RIFT_SECONDS_GROUP = 3
    private const val RIFT_MINUTES_GROUP = 1

    fun onModifyGame(text: Text, overlay: Boolean): Text {
        if (!overlay) return text
        var string = text.string

        // Health
        val healthMatch = ActionBarMatch.of(HEALTH_REGEX, string)
        health.update(healthMatch.intAt(HEALTH_GROUP))
        maxHealth.update(healthMatch.intAt(MAX_HEALTH_GROUP))
        string = healthMatch.strip(string)

        // Defense
        val defenseMatch = ActionBarMatch.of(DEFENSE_REGEX, string)
        defense.update(defenseMatch.intAt(DEFENSE_GROUP))
        string = defenseMatch.strip(string)
        val trueDefenseMatch = ActionBarMatch.of(TRUE_DEFENSE_REGEX, string)
        trueDefense.update(trueDefenseMatch.intAt(TRUE_DEFENSE_GROUP))
        string = trueDefenseMatch.strip(string)

        // Mana
        val manaMatch = ActionBarMatch.of(MANA_REGEX, string)
        mana.update(manaMatch.intAt(MANA_GROUP))
        maxMana.update(manaMatch.intAt(INTELLIGENCE_GROUP))
        overflowMana.update(manaMatch.intAt(OVERFLOW_MANA_GROUP))
        string = manaMatch.strip(string)

        // Rift Time
        val riftTimeMatch = ActionBarMatch.of(RIFT_TIME_REGEX, string)
        riftTimeSeconds.update(riftTimeMatch.intAt(RIFT_SECONDS_GROUP))
        riftTimeMinutes.update(riftTimeMatch.intAt(RIFT_MINUTES_GROUP))
        string = riftTimeMatch.strip(string)

        // Z. Sword
        val zombieSwordMatch = ActionBarMatch.of(ZOMBIE_SWORD_REGEX, string)
        zombieSwordAvailable.update(zombieSwordMatch.lengthAt(1))
        zombieSwordUsed.update(zombieSwordMatch.lengthAt(2))
        string = zombieSwordMatch.strip(string)

        return Text.of(string)
    }

}