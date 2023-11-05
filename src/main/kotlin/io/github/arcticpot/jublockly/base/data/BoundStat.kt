package io.github.arcticpot.jublockly.base.data

class BoundStat(private val value: IntStatValue, private val max: IntStatValue) {

    companion object {
        // Returns the limited part of the stat, whose overflow is ignored.
        fun limit(valueWithOverflow: StatValue<Int>, max: StatValue<Int>) =
            BoundStat(StatValueCalculation(valueWithOverflow, max) { minOf(valueWithOverflow.get(), max.get()) }, max)

        // Returns the overflow part of the stat.
        fun overflow(valueWithOverflow: StatValue<Int>, max: StatValue<Int>) =
            BoundStat(StatValueCalculation(valueWithOverflow, max) { maxOf(valueWithOverflow.get() - max.get(), 0) }, max)
    }

    private val current get() = value.get()

    private val total get() = max.get()

    val percentage get() = current.toDouble() / total

    val presenting get() = value.presenting && max.presenting
}

typealias IntStatValue = StatValueLike<Int>