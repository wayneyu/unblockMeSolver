package org.wayneyu.unblockme.solver

import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.search.BFS
import org.wayneyu.unblockme.solver.search.DFS
import org.wayneyu.unblockme.solver.search.Node
import java.util.*

class Solver(val xEndLoc: Int = 1) {

    fun solve(board: Board): List<Board> {
        val shortestPath = DFS.shortestPath(BoardNode(board, this))
        return shortestPath.map{ (it as BoardNode).board }
    }

    fun isSolved(board: Board): Boolean =
            board.redBar.xStart == xEndLoc && board.redBar.yStart == (board.ySize - 2)

}

data class BoardNode(val board: Board, val solver: Solver) : Node {
    override val neighbors: Set<Node>
        get() = board.neighbors.mapTo(HashSet<Node>()){ BoardNode(it, solver) }

    override fun isEnd(): Boolean = solver.isSolved(board)

    override fun toString(): String = board.layout
}

fun main(args: Array<String>) {

}