package io.github.arcticpot.jublockly.base.sbevents

import io.github.arcticpot.jublockly.base.sbevents.SkyBlockEvent.Companion.ANNUALLY
import io.github.arcticpot.jublockly.base.sbevents.SkyBlockEvent.Companion.BIANNUALLY
import io.github.arcticpot.jublockly.base.sbevents.SkyBlockEvent.Companion.THREE_DAYS
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.seconds

object SkyBlockEvents {
    val DARK_AUCTION =
        SkyBlockEvent(Instant.parse("2023-07-19T14:55:00Z"), interval = THREE_DAYS)

    val FARMING_CONTEST =
        SkyBlockEvent(Instant.parse("2023-07-19T14:15:00Z"), interval = THREE_DAYS, duration = 1200.seconds)

    val WINTER_ISLAND_OPEN =
        SkyBlockEvent(Instant.parse("2023-07-18T15:35:00Z"), interval = ANNUALLY)

    val JERRY_SEASON =
        SkyBlockEvent(Instant.parse("2023-07-18T23:15:00Z"), interval = ANNUALLY, duration = 3600.seconds)

    val NEW_YEAR =
        SkyBlockEvent(Instant.parse("2023-07-19T00:55:00Z"), interval = ANNUALLY, duration = 3600.seconds)

    val TRAVELING_ZOO =
        SkyBlockEvent(Instant.parse("2023-07-17T18:55:00Z"), interval = BIANNUALLY, duration = 3600.seconds)

    val SPOOKY =
        SkyBlockEvent(Instant.parse("2023-07-17T07:35:00Z"), interval = ANNUALLY, duration = 3600.seconds)

    val YEARLY_EVENTS = arrayOf(NEW_YEAR, TRAVELING_ZOO, SPOOKY, WINTER_ISLAND_OPEN, JERRY_SEASON)
}
