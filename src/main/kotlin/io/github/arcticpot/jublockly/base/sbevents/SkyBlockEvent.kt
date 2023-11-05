package io.github.arcticpot.jublockly.base.sbevents

import kotlinx.datetime.*
import kotlinx.datetime.Clock.System
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds


/**
 * @param reference The [Instant] of an example of the event. No need to be the first ever held one.
 * @param duration The duration of the event
 * @param interval The interval of the event
 */
open class SkyBlockEvent(private val reference: Instant, private val duration: Duration = ZERO, private val interval: Duration) {
    companion object {
        val ANNUALLY = 446400.seconds
        val BIANNUALLY = 223200.seconds
        val THREE_DAYS = 3600.seconds

        private val now get() = System.now()

        fun Array<SkyBlockEvent>.sortedByNextTime(): List<SkyBlockEvent> = this.sortedBy { it.next }
        fun Array<SkyBlockEvent>.filterActive(): List<SkyBlockEvent> = this.filter { it.isActive }
    }

    val last: Instant get() = reference + interval * (((now - reference) / interval).toInt())

    val next: Instant get() = last + interval

    val untilNext: Duration get() = next - now

    val sinceLast: Duration get() = now - last

    val untilConcluding: Duration get() = duration - sinceLast

    val isActive get() = if (duration <= ZERO) false else now - last < duration
}