package io.github.arcticpot.jublockly.base.dungeon.catacombs.rooms

private typealias IntPair = Pair<Int, Int>

class MapData(size: Int) {
    companion object {
        // Calculate the offset of two int pairs
        private fun offset(intPair1: IntPair, intPair2: IntPair): IntPair {
            return intPair1.first - intPair2.first to intPair1.second - intPair2.second
        }
    }

    private val rooms: Array<Array<Room?>> = Array(size) { arrayOfNulls(size) }

    // When testing the room the player is in, we have to redirect some "secondary" rooms to the "primary" room.
    // For example, for a 1x2 room, (2, 1) and (2, 2), where (2, 1) is the "primary" one, when you try to access (2, 2),
    // it returns (2, 1).
    // Time-memory trade-off.
    private val redirectionTable = mutableMapOf<IntPair, IntPair>()

    //
//    private val roomConnections = arrayListOf<Pair<IntPair, IntPair>>()


    // Merge two rooms into one
    fun merge(room1: IntPair, room2: IntPair): IntPair {

        // Rooms that are bigger than 1x1 use the left-most and top-most part.
        // For example, if a room is in a shape of `⣠`, the `⠠` part is used.

        // The Pair of (primaryRoom, secondaryRoom)
        val sortedRooms = sortRooms(room1, room2)

        // If the room is already in the redirection table, skip the function
        if (redirectionTable.containsKey(sortedRooms.second)) return sortedRooms.first

        // If the primary room is already in redirection table,
        // the secondary room redirects to where the primary room redirects to.
        if (redirectionTable.containsKey(sortedRooms.first)) {
            merge(redirectionTable[sortedRooms.first]!!, sortedRooms.second)
        }

        roomAt(sortedRooms.first)!!.shape.add(offset(sortedRooms.second, sortedRooms.first))

        rooms[sortedRooms.second.first][sortedRooms.second.second] = null
        redirectionTable[sortedRooms.second] = sortedRooms.first
        return sortedRooms.first
    }

    // Mark the two rooms are connected
//    fun connect(room1: IntPair, room2: IntPair) {
//        redirectionTable.put(room1, room2)
//    }

    private fun sortRooms(room1: IntPair, room2: IntPair): Pair<IntPair, IntPair> {
        return if (room1.second < room2.second) room1 to room2
            else if (room1.first < room2.first) room1 to room2
            else room2 to room1
    }

    fun roomAt(location: IntPair, redirection: Boolean = true): Room? {
        if (redirection && redirectionTable.containsKey(location)) {
            val actualLocation = redirectionTable[location]!!
            return rooms[actualLocation.first][actualLocation.second]
        }
        return rooms[location.first][location.second]
    }

    fun uncover(location: IntPair, room: Room) {
        val (first, second) =
            if (redirectionTable.containsKey(location)) redirectionTable[location]!! else location
        rooms[first][second] = room
    }


    fun isRoomUncovered(location: IntPair): Boolean {
        val (first, second) = location
        return rooms[first][second] != null || redirectionTable.containsKey(location)
    }

//    fun roomAt(x: Int, y: Int, redirection: Boolean = true) = roomAt(x to y, redirection)
}
