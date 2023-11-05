package io.github.arcticpot.jublockly.base.data

class StatValue<T>(initialValue: T): StatValueLike<T> {
    private var lastNonNull = initialValue
    private var current: T? = initialValue

    fun update(newValue: T?) {
        current = newValue
        if (newValue != null) lastNonNull = newValue
    }

    override val value get() = lastNonNull
    val valueNullable get() = current

//    fun getNullable() = value

    // If the value is not null, then the value must be detected.
    override val presenting get() = current != null
}
