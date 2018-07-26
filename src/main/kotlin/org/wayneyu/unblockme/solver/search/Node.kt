package org.wayneyu.unblockme.solver.search

interface Node {
    val neighbors: Set<Node>
    fun isEnd(): Boolean
}