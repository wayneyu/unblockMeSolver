package org.wayneyu.unblockme.solver

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xit
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.wayneyu.unblockme.solver.model.Bar
import org.wayneyu.unblockme.solver.model.Board
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

        xit("should solve two moves board") {
            /*
            |----|
            |--00|
            |--1-|
            |221-|
            */
            val expected = Board(4, 4, listOf(Bar(1, 2, 1, 2), Bar(2, 2, 0, 2), Bar(3, 0, 1, 2)))
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
            assertTrue(fixture.isSolved(finished))
            assertFalse(fixture.isSolved(board))
        }
    }

})