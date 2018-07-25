package org.wayneyu.unblockme.solver

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xit
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.wayneyu.unblockme.solver.model.Bar
import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.model.Move
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class SolverTest : Spek({

    describe("should solve simple 4x4 board") {

        val board = Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(1, 2, 0, 2), Bar(3, 0, 1, 2)))
        /*
        |----|
        |001-|
        |--1-|
        |22--|
        */
        val fixture = Solver()

        it("should solve board in two moves") {
            /*
            |----|
            |--00|
            |--1-|
            |221-|
            */
            val expected = listOf(
                    board,
                    board.move(1, Move(1, 0)),
                    board.move(1, Move(1, 0)).move(0, Move(2, 1)))
            
            assertEquals(expected, fixture.solve(board))
        }

        it("should mark as solved") {
            /*
            |----|
            |--00|
            |--1-|
            |--1-|
            */
            val finished = Board(4, 4, listOf(Bar(1, 2, 1, 2), Bar(2, 2, 0, 2)))
            assertTrue(Solver.isSolved(finished))
            assertFalse(Solver.isSolved(board))
        }
    }

})