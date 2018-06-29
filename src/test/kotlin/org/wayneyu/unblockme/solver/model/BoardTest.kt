package org.wayneyu.unblockme.solver.model

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xit
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
class BoardTest : Spek({

    describe("a bar") {

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
            assertFailsWith(Exception::class) {Bar(0, 0, 9, 2).getTiles()}
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
            assertFailsWith(Exception::class) {fixture + Move(2, 1)}
        }
    }

    describe("a board") {

        val board = Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(1, 2, 0, 2), Bar(3, 0, 1, 2)))
        /*
        |----|
        |001-|
        |--1-|
        |22--|
        */

        it("should return occupied tiles") {
            val expected = setOf(Tile(1,0), Tile(1,1), Tile(1,2), Tile(2,2), Tile(3,0), Tile(3,1))
            assertEquals(expected, board.occupiedTiles)
        }

        it("should build a 4 by 4 board") {
            val sb = StringBuilder()
            sb.appendln("|- - - -|")
            sb.appendln("|0 0 1 -|")
            sb.appendln("|- - 1 -|")
            sb.append(  "|2 2 - -|")

            assertEquals(sb.toString(), board.getLayout())
        }

        it("should move a bar") {
            /*
            |----|
            |00--|
            |--1-|
            |221-|
            */
            val expected = Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(2, 2, 0, 2), Bar(3, 0, 1, 2)))

            assertEquals(expected, board.move(1, Move(1, 0)))
        }

        it("should not change board if a move is not valid") {
            /*
            |----|
            |00--|
            |--1-|
            |221-|
            */
            val start = Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(2, 2, 0, 2), Bar(3, 0, 1, 2)))
            assertEquals(start, start.move(2, Move(1, 1)))
        }

        xit("should create its neighbors") {
            val expectedNeighbors = listOf(
                    Board(4, 4, listOf(Bar(1, 1, 1, 2), Bar(2, 2, 0, 2), Bar(3, 0, 1, 2))),
                    Board(4, 4, listOf(Bar(1, 2, 1, 2), Bar(2, 2, 0, 2), Bar(3, 0, 1, 2))),
                    Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(0, 2, 0, 2), Bar(3, 0, 1, 2))),
                    Board(4, 4, listOf(Bar(1, 0, 1, 2), Bar(1, 2, 0, 2), Bar(3, 0, 1, 2))))

            assertEquals(expectedNeighbors, board.neighbors)
        }
    }

    describe("a move") {

        val fixture = Move(1, 1)

        it("should add another move") {
            assertEquals(fixture + Move(2, 1), Move(3, 1))
        }

        it("should throw exception when adding another move in different direction") {
            assertFailsWith(Exception::class) {fixture + Move(2, 0)}
        }
    }
})