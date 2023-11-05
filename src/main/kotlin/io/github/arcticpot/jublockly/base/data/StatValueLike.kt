package io.github.arcticpot.jublockly.base.data

interface StatValueLike<T> {
    val value: T
    val presenting: Boolean
}