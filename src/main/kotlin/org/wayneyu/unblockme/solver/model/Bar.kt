package org.wayneyu.unblockme.solver.model

data class Bar(val xstart: Int,
               val ystart: Int,
               val direction: Int, //direction of bar. 0: x-dir, 1: y-dir
               val length: Int) {

    fun getTiles(): List<Tile> = when (direction) {
        0 -> (0 until length)
                .toList()
                .map{ Tile(xstart + it, ystart) }
        1 -> (0 until length)
                .toList()
                .map{ Tile(xstart, ystart + it) }
        else -> throw Exception("Invalid direction")
    }
}

data class Tile(val x: Int,
                val y: Int)