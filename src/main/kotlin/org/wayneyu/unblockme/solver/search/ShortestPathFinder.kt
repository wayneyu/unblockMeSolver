package org.wayneyu.unblockme.solver.search

import org.slf4j.Logger

interface ShortestPathFinder {

    val logger: Logger

    fun search(root: Node): SearchResult

    fun shortestPath(root: Node): List<Node> {
        val searchResult = search(root)
        val shortestPath = shortestPathFromEndToStart(searchResult.endNode, searchResult.shortestParent).reversed()
        logger.info("Search finished in ${searchResult.iterations} iterations, shortest path: ${shortestPath.size - 1} steps")
        return shortestPath
    }

    fun shortestPathFromEndToStart(node: Node, shortestParent: Map<Node, Node>): List<Node> {
        val parent = shortestParent[node] ?: throw Exception("should not have no match")
        return if (parent == node) listOf(node) else listOf(node).plus(shortestPathFromEndToStart(parent, shortestParent))
    }
}