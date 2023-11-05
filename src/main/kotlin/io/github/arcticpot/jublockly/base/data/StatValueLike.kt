package io.github.arcticpot.jublockly.base.data

interface StatValueLike<T> {
    fun get(): T

    val value: T

    val presenting: Boolean
}