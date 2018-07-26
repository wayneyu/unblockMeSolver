package org.wayneyu.unblockme.solver.search

interface ShortestPathFinder {

    fun search(root: Node): SearchResult

    fun shortestPath(root: Node): List<Node> {
        val searchResult = this.search(root)
        println("iter: ${searchResult.iterations}")
        return this.shortestPathFromEndToStart(searchResult.endNode, searchResult.shortestParent).reversed()
    }

    fun shortestPathFromEndToStart(node: Node, shortestParent: Map<Node, Node>): List<Node> {
        val parent = shortestParent[node] ?: throw Exception("should not have no match")
        return if (parent == node) listOf(node) else listOf(node).plus(shortestPathFromEndToStart(parent, shortestParent))
    }
}