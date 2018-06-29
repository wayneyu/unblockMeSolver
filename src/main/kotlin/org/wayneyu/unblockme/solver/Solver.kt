package org.wayneyu.unblockme.solver

import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.model.Move

class Solver {

    fun solve(board: Board): Board = board

    fun isSolved(board: Board): Boolean =
        board.redBar.xStart == 1 && board.redBar.yStart == (board.ySize - 2)



}

fun main(args: Array<String>) {

}