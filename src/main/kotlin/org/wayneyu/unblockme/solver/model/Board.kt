package org.wayneyu.unblockme.solver.model

data class Board(val xSize: Int,
                 val ySize: Int,
                 val bars: List<Bar>){ // first bar is red bar

    // -1 for empty space, 0 for red bar, 1..n for other bars
    private val board: Array<IntArray> by lazy {
        val brd = (1..ySize).map { IntArray(xSize) { -1 } }.toTypedArray()
        bars.withIndex().forEach{ (index, bar) -> setBar(brd, bar, index) }
        brd
    }

    private fun setBar(board: Array<IntArray>, bar: Bar, value: Int) = bar.getTiles().forEach{board[it.x][it.y] = value}

    fun move(barIndex: Int, move: Move): Board {
        val mutableBars = bars.toMutableList()
        mutableBars[barIndex] = mutableBars[barIndex] + move
        return Board(xSize, ySize, mutableBars)
    }

    fun getBoardString() = this.board.toList()
            .map{it.joinToString(" ", "|", "|").replace("-1", "-")}.joinToString("\n")
}

data class Bar(val xStart: Int, // vertical direction, top to bottom
               val yStart: Int, // horizontal direction, left to right
               val direction: Int, //direction of bar. 0: x-dir, 1: y-dir
               val length: Int) {

    fun getTiles(): List<Tile> = when (direction) {
        0 -> (0 until length)
                .toList()
                .map{ Tile(xStart + it, yStart) }
        1 -> (0 until length)
                .toList()
                .map{ Tile(xStart, yStart + it) }
        else -> throw Exception("Invalid direction")
    }

    operator fun plus(move: Move): Bar {
        if (direction != move.direction)
            throw Exception("Directions have to be the same")

        return when (direction) {
            0 -> this.copy(xStart = xStart + move.nTile)
            1 ->  this.copy(yStart = yStart + move.nTile)
            else -> throw Exception("Invalid direction")
        }
    }

}

data class Tile(val x: Int,
                val y: Int)

data class Move(val nTile: Int,
                val direction: Int) {

    operator fun plus(another: Move): Move {
        if (direction == another.direction)
            return Move(nTile + another.nTile, direction)
        else
            throw Exception("Directions have to be the same")
    }
}