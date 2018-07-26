package org.wayneyu.unblockme.solver.search

import org.slf4j.LoggerFactory
import java.util.*

object DFS : ShortestPathFinder {

    override val logger = LoggerFactory.getLogger(DFS::class.toString())

    override fun search(root: Node): SearchResult {

        val visited = mutableSetOf<Node>()
        val stack = Stack<Node>()
        val shortestDistToNode = mutableMapOf(root to 0)
        val shortestParentToNode = mutableMapOf(root to root)
        var shortestDistEndNode: Pair<Node, Int> = root to Int.MAX_VALUE

        stack.add(root)
        var node = root
        var iter = 0
        while(stack.isNotEmpty()) {
            iter++
            node = stack.pop()

            val distToNode = shortestDistToNode[node] ?: throw Exception("Should not have no match")

            if (node.isEnd() && distToNode < shortestDistEndNode.second) {
                shortestDistEndNode = node to distToNode
            }

            val neighbors = node.neighbors
            neighbors.forEach {
                if (shortestDistToNode[it] ?: Int.MAX_VALUE > distToNode + 1) { // not exist = not reached, set to Int.MAX_VALUE
                    shortestDistToNode.put(it, distToNode + 1)
                    shortestParentToNode.put(it, node)
                }
            }
            val notVisitedNeighbors = neighbors.filterNot { visited.contains(it) }
            stack.addAll(notVisitedNeighbors)

            visited.add(node)
        }

        return SearchResult(shortestDistEndNode.first, shortestParentToNode, iter)
    }

}