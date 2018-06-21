package org.wayneyu.unblockme.solver.model

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertArrayEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
class BoardTest : Spek({

    describe("should get tiles of bar") {

        it("should create tiles for bars in x direction") {
            val xbar = Bar(1, 2, 0, 3)
            val expected = listOf(Tile(1, 2), Tile(2, 2), Tile(3, 2))
            assertEquals(expected, xbar.getTiles())
        }

        it("should create tiles for bars in y direction") {
            val ybar = Bar(2, 1, 1, 3)
            val expected = listOf(Tile(2, 1), Tile(2, 2), Tile(2, 3))
            assertEquals(expected, ybar.getTiles())
        }

        it("should throw Exception when bar direction is not 0 or 1") {
            assertFailsWith(Exception::class, {Bar(0, 0, 9, 2).getTiles()})
        }

        it("should change x location when adding a move in x direction") {
            val fixture = Bar(1,2,0,2)
            val move = Move(2,0)
            assertEquals(fixture + Move(2, 0), fixture.copy(xStart = fixture.xStart + move.nTile))
        }

        it("should change y location when adding a move in y direction") {
            val fixture = Bar(1,2,1,2)
            val move = Move(2,1)
            assertEquals(fixture + Move(2, 1), fixture.copy(yStart = fixture.yStart + move.nTile))
        }

        it("should throw Exception when adding a move in different direction") {
            val fixture = Bar(1, 2, 0, 3)
            assertFailsWith(Exception::class, {fixture + Move(2, 1)})
        }
    }

    describe("should build board") {

        val board = Board(4, 4, 1, 0, listOf(Bar(1, 2, 0, 2), Bar(3, 0, 1, 2)))
        /*
        |----|
        |RR1-
        |--1-|
        |22--|
        */

        it("should build a 4 by 4 board") {
            val expected: Array<IntArray> = arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(-1, -1, 1, 0),
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(2, 2, 0, 0))

            assertArrayEquals(expected, board.board)
        }

        it("should get string for the board") {
            val sb = StringBuilder()
            sb.appendln("|0 0 0 0|")
            sb.appendln("|-1 -1 1 0|")
            sb.appendln("|0 0 1 0|")
            sb.append("|2 2 0 0|")

            assertEquals(sb.toString(), board.getBoardString())
        }

    }

    describe("should test moves") {

        val fixture = Move(1,1)

        it("should add another move") {
            assertEquals(fixture + Move(2, 1), Move(3, 1))
        }

        it("should throw exception when adding another move in different direction") {
            assertFailsWith(Exception::class, {fixture + Move(2, 0)})
        }
    }
})