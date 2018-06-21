package org.wayneyu.unblockme.solver.model

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertArrayEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

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
})