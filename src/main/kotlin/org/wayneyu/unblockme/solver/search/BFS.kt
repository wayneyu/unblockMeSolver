package org.wayneyu.unblockme.solver.search

import org.slf4j.LoggerFactory

object BFS : ShortestPathFinder {

    override val logger = LoggerFactory.getLogger(BFS::class.toString())

    override fun search(root: Node): SearchResult {
        val visited = mutableSetOf<Node>()
        val queue = mutableListOf<Node>()
        val shortestDistToNode = mutableMapOf<Node, Int>()
        val shortestParentToNode = mutableMapOf<Node, Node>()

        var node = root
        queue.add(root)
        visited.add(root)
        shortestDistToNode.put(root, 0)
        shortestParentToNode.put(root, root) // set parent of root to root

        var iter = 0
        while(queue.isNotEmpty()) {
            iter++
            node = queue.removeAt(0)

            if (!node.isEnd()) {
                val distToNode = shortestDistToNode[node] ?: throw Exception("Should not have no match") // shouldnt return no match
                val neighbors = node.neighbors
                neighbors.forEach {
                    if (shortestDistToNode.getOrElse(it){Integer.MAX_VALUE} > distToNode + 1) { // not exist = not reached, set to Int.MAX_VALUE
                        shortestDistToNode.put(it, distToNode + 1)
                        shortestParentToNode.put(it, node)
                    }
                }
                val notVisitedNeighbors = neighbors.filterNot { visited.contains(it) }
                queue.addAll(notVisitedNeighbors)
                visited.addAll(notVisitedNeighbors)
            } else {
                break
            }
        }

        return SearchResult(node, shortestParentToNode, iter)
    }

}