package org.wayneyu.unblockme.solver.search

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
class BFSTest : Spek({

    data class DummyNode(val name: String,
                         override val neighbors: Set<Node>,
                         val isEndNode: Boolean) : Node {

        override fun isEnd(): Boolean = isEndNode
        override fun toString(): String = name
    }

    describe("Breadth first search of graph") {

        /*
            root -> n1 -> n2 -> n3
                 -> n3 -> end
        */
        val end = DummyNode("end", emptySet<Node>(), true)
        val n3 = DummyNode("n3", setOf(end), false)
        val n2 = DummyNode("n2", setOf(n3), false)
        val n1 = DummyNode("n1", setOf(n2), false)
        val root = DummyNode("root", setOf(n1, n3), false)


        it("should return end node and shortest parent to each node") {
            val actual = BFS.search(root)
            val expectedShortestParentMap = mapOf<Node, Node>(root to root, n1 to root, n2 to n1, n3 to root, end to n3)

            assertEquals(end, actual.endNode)
            assertEquals(expectedShortestParentMap, actual.shortestParent)
        }

        it("should construct shortest path reversed from search result") {
            val search = BFS.search(root)
            val actual = BFS.shortestPathFromEndToStart(search.endNode, search.shortestParent)
            val expected = listOf(end, n3, root)

            assertEquals(expected, actual)
        }

        it("should return shortest path") {
            assertEquals(listOf(root, n3, end), BFS.shortestPath(root))
        }
    }



})