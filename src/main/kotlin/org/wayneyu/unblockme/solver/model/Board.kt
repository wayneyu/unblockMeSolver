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

    private fun isWithinBoard(bar: Bar) =
        bar.getTiles().filter { it.x < 0 || it.x >= xSize || it.y < 0 || it.y >= ySize }.isEmpty()

    fun moveBar(barIndex: Int, move: Move): Board? {
        val newBar = bars[barIndex] + move

        val isMoveValid = isWithinBoard(newBar) &&
                (this.occupiedTiles - bars[barIndex].getTiles()).intersect(newBar.getTiles()).isEmpty()

        return if(isMoveValid) {
            val mutableBars = bars.toMutableList()
            mutableBars[barIndex] = mutableBars[barIndex] + move
            this.copy(bars = mutableBars) }
        else null

    }

    fun moveBars(moves: List<Pair<Int, Move>>): List<Board> {
        if (moves.isEmpty()) return emptyList()
        val boardAfterFirstMove = moveBar(moves.first().first, moves.first().second) ?: this
        return listOf(boardAfterFirstMove).plus(boardAfterFirstMove.moveBars(moves.drop(1)))
    }

    val occupiedTiles: Set<Tile> = bars.flatMap { it.getTiles() }.toSet()

    val redBar: Bar
        get() = bars[0]

    val neighbors: Set<Board> // all boards that are reachable with one moveBars
        get() = bars
                .mapIndexed { index, bar ->
                    val maxSteps = if (bar.direction == 0) xSize - bar.length else ySize - bar.length
                    val backwardMoves = (-1 downTo -maxSteps).map{ Move(it, bar.direction) }
                    val forwardMoves = (1 .. maxSteps).map{ Move(it, bar.direction) }
                    backwardMoves.takeWhile { moveBar(index, it) != null } + forwardMoves.takeWhile { moveBar(index, it) != null } }
                .mapIndexed { index, moves ->
                    moves.mapNotNull{ moveBar(index, it) } }
                .flatten()
                .toSet()

    val layout: String
        get() = this.board.toList()
                .map { it.map{Integer.toHexString(it)} }
                .mapIndexed { x, row ->
                    val suffix = if (x != redBar.xStart) "|" else " "
                    row.joinToString(" ", "|", suffix).replace("ffffffff", "-") }
                .joinToString("\n")
}

data class Bar(val xStart: Int, // vertical direction, top to bottom
               val yStart: Int, // horizontal direction, left to right
               val direction: Int, //direction of bar. 0: x-dir, 1: y-dir
               val length: Int) {

    companion object {
        fun fromTiles(tiles: List<Tile>): Bar {
            val xs = tiles.map{ it.x }.toSet()
            val ys = tiles.map{ it.y }.toSet()

            val xmin = xs.min() ?: throw Exception("Tiles don't have x direction")
            val ymin = ys.min() ?: throw Exception("Tiles don't have y direction")

            return if (xs.size == 1) {
                Bar(xs.first(), ymin, 1, ys.size)
            } else {
                Bar(xmin, ys.first(), 0, xs.size)
            }
        }
    }

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
            0 -> this.copy(xStart = xStart + move.offset)
            1 -> this.copy(yStart = yStart + move.offset)
            else -> throw Exception("Invalid direction")
        }
    }

}

data class Tile(val x: Int,
                val y: Int)

data class Move(val offset: Int,
                val direction: Int) {

    operator fun plus(another: Move): Move {
        if (direction == another.direction)
            return Move(offset + another.offset, direction)
        else
            throw Exception("Directions have to be the same")
    }
}