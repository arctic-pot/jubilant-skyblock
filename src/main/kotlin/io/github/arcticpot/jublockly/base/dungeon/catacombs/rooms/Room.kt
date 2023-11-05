package io.github.arcticpot.jublockly.base.dungeon.catacombs.rooms

class Room(val category: RoomCategory, type: RoomType? = null) {
    companion object {
        enum class CheckmarkState { NONE, WHITE, GREEN }
    }

    val shape = mutableListOf(Pair(0, 0))

    private var roomType: RoomType? = type

    var checkmark: CheckmarkState = CheckmarkState.NONE

    val filled get() = roomType != null

    fun fill(type: RoomType) {
        roomType = type
    }
}


