package org.wayneyu.unblockme.solver.parser

import org.wayneyu.unblockme.solver.model.Bar
import org.wayneyu.unblockme.solver.model.Board
import org.wayneyu.unblockme.solver.model.Tile

object BoardParser {

    fun createBoard(rows: List<String>): Board { // each row contains the either - or bar index (base 16)
        val xsize = rows.size
        val ysize = rows[0].length

        val tiles = mutableMapOf<Int, List<Tile>>()

        rows.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char != '-') {
                    val barIndex = Integer.parseInt(char.toString(), 16)
                    val tile = Tile(x, y)
                    tiles.put(barIndex, tiles[barIndex]?.plus(tile) ?: listOf(tile))
                }
            }
        }

        val bars: List<Bar> = tiles.toSortedMap().values.map { Bar.fromTiles(it) }

        return Board(xsize, ysize, bars)
    }

}