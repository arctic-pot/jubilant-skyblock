package io.github.arcticpot.jublockly.base

import java.time.Instant
import java.util.Date

class SkyBlockTime(val year: Int, val season: Season, val date: Int) {
    enum class Season {
        EarlySpring,
        Spring,
        LateSpring,
        EarlySummer,
        Summer,
        LateSummer,
        EarlyAutumn,
        Autumn,
        LateAutumn,
        EarlyWinter,
        Winter,
        LateWinter;
    }

    companion object {
        const val EPOCH_STAMP = 1560275700
        const val SKB_YEAR_TO_IRL_TIME = 1560275700

    }

    private fun fromTimestamp(stamp: Long): SkyBlockTime {
        val year = ((stamp - EPOCH_STAMP) / SKB_YEAR_TO_IRL_TIME + 1).toInt()
        // The IRL seconds passed during the current year
        val yearSeconds = ((stamp - EPOCH_STAMP) % SKB_YEAR_TO_IRL_TIME).toInt()
        // The SKB days passed during the current year
        val yearDate = yearSeconds / 1200 + 1
        val seasonIndex = yearDate / 31
        // The SKB days passed during the current season
        // That is to say, for a date '4th of Late Spring', `seasonDate` is 4.
        val seasonDate = yearDate % 31
        val season = Season.values()[seasonIndex]
        return SkyBlockTime(year, season, seasonDate)
    }

    val now: SkyBlockTime get() {
        return fromTimestamp(Instant.now().epochSecond)
    }

    val nowOnAHN: SkyBlockTime get() {
        return fromTimestamp(Instant.now().epochSecond + 211680);
    }

    fun toTimestamp(): Long {
        return ((year - 1) * SKB_YEAR_TO_IRL_TIME + (season.ordinal * 31 + date) * 1200 + EPOCH_STAMP).toLong()
    }


}