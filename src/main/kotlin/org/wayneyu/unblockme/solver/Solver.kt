package org.wayneyu.unblockme.solver

import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.search.Node
import org.wayneyu.unblockme.solver.search.ShortestPathFinder
import java.util.*

class Solver(val pathFinder: ShortestPathFinder) {

    fun solve(board: Board): List<Board> {
        val shortestPath = pathFinder.shortestPath(BoardNode(board, this))
        return shortestPath.map{ (it as BoardNode).board }
    }

    fun isSolved(board: Board): Boolean = board.redBar.yStart == board.ySize - 2

}

data class BoardNode(val board: Board, val solver: Solver) : Node {
    override val neighbors: Set<Node>
        get() = board.neighbors.mapTo(HashSet<Node>()){ BoardNode(it, solver) }

    override fun isEnd(): Boolean = solver.isSolved(board)

    override fun toString(): String = board.layout
}

fun main(args: Array<String>) {

}