package org.wayneyu.unblockme.solver

import org.wayneyu.unblockme.solver.model.Board
import java.util.*

class Solver {

    fun solve(board: Board): List<Board> {
        val shortestPath = BFS.shortestPath(BoardNode(board))
        return shortestPath.map{ (it as BoardNode).board }
    }

    companion object {
        fun isSolved(board: Board): Boolean =
                board.redBar.xStart == 1 && board.redBar.yStart == (board.ySize - 2)
    }

}

data class BoardNode(val board: Board) : Node {
    override val neighbors: Set<Node>
        get() = board.neighbors.mapTo(HashSet<Node>()){ BoardNode(it) }

    override fun isEnd(): Boolean = Solver.isSolved(board)
}

fun main(args: Array<String>) {

}