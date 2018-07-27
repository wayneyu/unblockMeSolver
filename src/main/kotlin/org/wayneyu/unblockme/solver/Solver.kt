package org.wayneyu.unblockme.solver

import org.slf4j.LoggerFactory
import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.parser.BoardParser
import org.wayneyu.unblockme.solver.search.BFS
import org.wayneyu.unblockme.solver.search.Node
import org.wayneyu.unblockme.solver.search.ShortestPathFinder
import java.io.File
import java.util.*

class Solver(private val pathFinder: ShortestPathFinder) {

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

    val logger = LoggerFactory.getLogger(Solver::class.java)

    try {
        val filePath = args[0]
        val lines = File(".", filePath).readLines()

        val board = BoardParser.createBoard(lines)
        val solution = Solver(BFS).solve(board)
        logger.info("Solution: \n${solution.joinToString("\n\n") { it.layout }}")
    } catch (ex: ArrayIndexOutOfBoundsException){
        throw Exception("Not enough input parameters. Usage: java -jar this.jar filename")
    }
}