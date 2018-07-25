package org.wayneyu.unblockme.solver

interface Node {
    val neighbors: Set<Node>
    fun isEnd(): Boolean
}