package org.wayneyu.unblockme.solver.parser

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.wayneyu.unblockme.solver.model.Bar
import org.wayneyu.unblockme.solver.model.Board
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class BoardParserTest : Spek({

    describe("board parser") {

        it("should create board from board string layout") {
            val layout = """
                -12344
                -12355
                002---
                6777-A
                6----A
                8899-A
                """.trimIndent()

            val expected = Board(6, 6, listOf(
                Bar(2, 0, 1, 2),
                Bar(0, 1, 0, 2),
                Bar(0, 2, 0, 3),
                Bar(0, 3, 0, 2),
                Bar(0, 4, 1, 2),
                Bar(1, 4, 1, 2),
                Bar(3, 0, 0, 2),
                Bar(3, 1, 1, 3),
                Bar(5, 0, 1, 2),
                Bar(5, 2, 1, 2),
                Bar(3, 5, 0, 3)))

            val actual = BoardParser.createBoard(layout.split("\n"))
            assertEquals(expected, actual)
        }
    }
})