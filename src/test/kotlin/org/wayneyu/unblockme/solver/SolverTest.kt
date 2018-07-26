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
import org.wayneyu.unblockme.solver.parser.BoardParser
import org.wayneyu.unblockme.solver.search.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(JUnitPlatform::class)
class SolverTest : Spek({

    val fixture = Solver(BFS)

    val board4x4 = Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(1, 2, 0, 2), Bar(3, 0, 1, 2)))
    /*
    |----|
    |001-
    |--1-|
    |22--|
    */

    describe("solver") {
        it("should mark as solved") {
            /*
            |----|
            |--00
            |--1-|
            |--1-|
            */
            val finished = Board(4, 4, listOf(Bar(1, 2, 1, 2), Bar(2, 2, 0, 2)))
            assertTrue(fixture.isSolved(finished))
            assertFalse(fixture.isSolved(board4x4))
        }
    }

    describe("should solve simple 4x4 board") {

        it("should solve board in two moves") {
            /*
            |----|
            |--00
            |--1-|
            |221-|
            */
            val expected = listOf(
                    board4x4,
                    board4x4.move(1, Move(1, 0)),
                    board4x4.move(1, Move(1, 0))?.move(0, Move(2, 1)))

            assertEquals(expected, fixture.solve(board4x4))
        }

    }

    describe("should solve 5x5 board") {

        it("should solve board in four moves") {
            /*
            |--31-|
            |0031-
            |--31-|
            |-22--|
            |-----|
            */
            val board5x5 = Board(5, 5, listOf(Bar(1, 0, 1, 2), Bar(0, 3, 0, 3), Bar(3, 1, 1, 2), Bar(0, 2, 0, 3)))
            val expected = listOf(board5x5).plus(board5x5.move(listOf(
                    2 to Move(-1, 1),
                    3 to Move(2, 0),
                    1 to Move(2, 0),
                    0 to Move(3, 1))))
            val actual = fixture.solve(board5x5)

            assertEquals(expected.size, actual.size)
        }
    }

    describe("should solve 6x6 board") {

        it("should solve board (Beginner original 96) in 15 moves ") {
            val layout = """
            |--2344|
            |11235-|
            |60035-
            |678899|
            |-7A--C|
            |--ABBC|
            """.trimIndent().replace("|", "").split("\n")

            val board6x6 = BoardParser.createBoard(layout)

            val actual = fixture.solve(board6x6)
            assertEquals(15, actual.size - 1)
        }

        it("should solve board (Intermediate original 1) in 21 moves ") {
            val layout = """
            |-12344|
            |-12355|
            |002---
            |6777-A|
            |6----A|
            |8899-A|
            """.trimIndent().replace("|", "").split("\n")

            val board6x6 = BoardParser.createBoard(layout)
            val actual = fixture.solve(board6x6)

            assertEquals(21, actual.size - 1)
        }
    }

})
