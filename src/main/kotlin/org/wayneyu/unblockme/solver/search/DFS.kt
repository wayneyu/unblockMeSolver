package org.wayneyu.unblockme.solver.search

import java.util.*

object DFS : ShortestPathFinder {

    override fun search(root: Node): SearchResult {

        val visited = mutableSetOf<Node>()
        val stack = Stack<Node>()
        val shortestDistToNode = mutableMapOf<Node, Int>()
        val shortestParentToNode = mutableMapOf<Node, Node>()

        var node = root
        stack.add(root)
        shortestDistToNode.put(root, 0)
        shortestParentToNode.put(root, root) // set parent of root to root
        while(stack.isNotEmpty()) {
            node = stack.pop()
            println(node.toString() + "\n")
            if (!node.isEnd()) {
                val distToNode = shortestDistToNode[node] ?: throw Exception("Should not have no match") // shouldnt return no match
                val neighbors = node.neighbors
                neighbors.forEach {
                    if (shortestDistToNode[it] ?: Int.MAX_VALUE > distToNode + 1) { // not exist = not reached, set to Int.MAX_VALUE
                        shortestDistToNode.put(it, distToNode + 1)
                        shortestParentToNode.put(it, node)
                    }
                }
                val notVisitedNeighbors = node.neighbors.filterNot { visited.contains(it) }
                stack.addAll(notVisitedNeighbors)
            } else {
                break
            }
            visited.add(node)
        }

        return SearchResult(node, shortestParentToNode)
    }

}