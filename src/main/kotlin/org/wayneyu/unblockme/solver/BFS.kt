import org.wayneyu.unblockme.solver.Node

object BFS {

    data class SearchResult(val endNode: Node,
                            val shortestParent: Map<Node, Node>)


    fun shortestPath(root: Node): List<Node> {
        val searchResult = bfs(root)
        return shortestPathFromEndToStart(searchResult.endNode, searchResult.shortestParent).reversed()
    }

    fun bfs(root: Node): SearchResult {
        val visited = mutableSetOf<Node>()
        val queue = mutableListOf<Node>()
        val shortestDistToNode = mutableMapOf<Node, Int>()
        val shortestParentToNode = mutableMapOf<Node, Node>()

        var node = root
        queue.add(root)
        shortestDistToNode.put(root, 0)
        shortestParentToNode.put(root, root) // set parent of root to root
        while(queue.isNotEmpty()) {
            node = queue.removeAt(0)
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
                queue.addAll(notVisitedNeighbors)
            } else {
                break
            }
            visited.add(node)
        }

        return SearchResult(node, shortestParentToNode)
    }

    fun shortestPathFromEndToStart(node: Node, shortestParent: Map<Node, Node>): List<Node> {
        val parent = shortestParent[node] ?: throw Exception("should not have no match")
        return if (parent == node) listOf(node) else listOf(node).plus(shortestPathFromEndToStart(parent, shortestParent))
    }
}