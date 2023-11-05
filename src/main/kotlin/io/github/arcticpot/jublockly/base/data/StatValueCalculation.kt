package io.github.arcticpot.jublockly.base.data

class StatValueCalculation<T>(private vararg val dependencies: StatValue<*>, private val evaluator: () -> T): StatValueLike<T> {
    override val value get() = evaluator.invoke()

    override val presenting get() = dependencies.all { it.presenting }

//    If it causes trouble...
//    override val presenting get() = dependencies[0].presenting
}