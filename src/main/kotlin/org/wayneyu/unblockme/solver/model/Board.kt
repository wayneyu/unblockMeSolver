package org.wayneyu.unblockme.solver.model

data class Board(val xsize: Int,
                 val ysize: Int,
                 val mainBarX: Int,
                 val mainBarY: Int,
                 val bars: List<Bar>) {

    fun getBoard(): Array<IntArray> {
        val board = (1..ysize).map{IntArray(xsize)}.toTypedArray()
        setBar(board, Bar(mainBarX, mainBarY, 1, 2), -1)
        bars.withIndex().forEach { setBar(board, it.value, it.index + 1) }
        return board
    }

    private fun setBar(board: Array<IntArray>, bar: Bar, value: Int) = bar.getTiles().forEach{board[it.x][it.y] = value}
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
}

data class Tile(val x: Int,
                val y: Int)
