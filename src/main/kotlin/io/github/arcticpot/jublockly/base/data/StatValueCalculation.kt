package io.github.arcticpot.jublockly.base.data

class StatValueCalculation<T>(vararg val dependencies: StatValue<*>, val function: () -> T): StatValueLike<T> {
    override val value get() = function()

    override val presenting get() = !dependencies.any { !it.presenting }
}