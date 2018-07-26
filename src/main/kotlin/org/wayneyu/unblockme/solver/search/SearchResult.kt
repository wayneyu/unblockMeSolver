package org.wayneyu.unblockme.solver.search

data class SearchResult(val endNode: Node,
                        val shortestParent: Map<Node, Node>,
                        val iterations: Int = -1)