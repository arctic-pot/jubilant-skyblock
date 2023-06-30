package io.github.arcticpot.jublockly.utils.gui

enum class HorizontalAlignment {
    Right,
    Left,
    Center;

    fun getOffsetX(contentWidth: Int, boxWidth: Int = 0): Int {
        assert(boxWidth == 0 || boxWidth < contentWidth)
        return when (this) {
            Right -> boxWidth - contentWidth
            Left -> 0
            Center -> (boxWidth - contentWidth) / 2
        }
    }
}