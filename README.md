# UnblockMe Solver written in Kotlin


### How to run
```
mvn clean install package
```
```
java -jar target/unblockme-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/beginnerOriginal96Board
```

## About the game
UnblockMe is a puzzle game from Kira Games

A sample puzzle looks like this

![Puzzle 96 from Easy Original package](https://github.com/wayneyu/unblockMeSolver/blob/master/doc/Screenshot_20180726-203231_resized.jpg)

The rules of the game are fairly simple:

1. Each block, including the red block, can only be moved in the direction of the length of the block, i.e. a horizontal block moves horizontally and vertical block vertically.
1. A block can be moved into any empty space that another block does not occupy.
1. The objective of the game is unblock the red block and get it pass the right edge of the board.

Fairly simple rules, but it can be fairly challenging to solve, especially trying to crack a puzzle within the optimal number of moves given, which in this case is 15.


## The solver

### Setup
This solver parses a board in text format and represents it by its dimensions and a list of `Bar`s:
```
data class Board(val xSize: Int,
                 val ySize: Int,
                 val bars: List<Bar>)
```

A `Bar` is represented by the location of the left most or top most tile of the bar, its length and the direction:
```
data class Bar(val xStart: Int, // vertical direction, top to bottom
               val yStart: Int, // horizontal direction, left to right
               val direction: Int, //direction of bar. 0: x-dir, 1: y-dir
               val length: Int)
```
Note that `(x, y) = (0, 0)` is top left corner of the board and `x` is the vertical direction.

For example:
```
|- - - - - -|
|1 1 - 2 - -|
|- 0 0 2 - -
|- - - - - -|
|- - - - - -|
|- - - - - -|
```
is represented by
```
Board(xSize = 6, ySize = 6, bars = listOf(Bar(2, 1, 1, 2), Bar(1, 0, 1, 2), Bar(1, 3, 0, 2)))
```

### Method
This is a shortest path finding problem and we solve it using BFS on a graph, where each node is a `Board` and node neighbors are the possible boards reachable by moving a Bar once.  

To find the shortest path, we keep track of the shortest distance to reach a board and the parent node leading to the board.  

At the end, we trace back from the end node to generate the solution.  

This algorithm is essentially Djikstra's algorithm where edges are all 1 and using a queue to keep track of nodes.  

```
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
```

### Result
Shortest path solution af the sample puzzle was reached in ~2400 node visits and took about 1.2s

```
|- - 2 3 4 4|
|1 1 2 3 5 -|
|6 0 0 3 5 -
|6 7 8 8 9 9|
|- 7 a - - c|
|- - a b b c|
```
```
|- - 2 3 4 4|
|1 1 2 3 5 -|
|- 0 0 3 5 -
|- 7 8 8 9 9|
|6 7 a - - c|
|6 - a b b c|
```
```
|- - 2 3 4 4|
|1 1 2 3 5 -|
|- 0 0 3 5 -
|- - 8 8 9 9|
|6 7 a - - c|
|6 7 a b b c|
```
```
|- - 2 3 4 4|
|1 1 2 3 5 -|
|- 0 0 3 5 -
|8 8 - - 9 9|
|6 7 a - - c|
|6 7 a b b c|
```
```
|- - 2 3 4 4|
|1 1 2 3 5 -|
|0 0 - 3 5 -
|8 8 - - 9 9|
|6 7 a - - c|
|6 7 a b b c|
```
```
|- - - 3 4 4|
|1 1 2 3 5 -|
|0 0 2 3 5 -
|8 8 - - 9 9|
|6 7 a - - c|
|6 7 a b b c|
```
```
|- - - 3 4 4|
|1 1 2 3 5 -|
|0 0 2 3 5 -
|8 8 9 9 - -|
|6 7 a - - c|
|6 7 a b b c|
```
```
|- - - 3 4 4|
|1 1 2 3 5 c|
|0 0 2 3 5 c
|8 8 9 9 - -|
|6 7 a - - -|
|6 7 a b b -|
```
```
|- - - 3 4 4|
|1 1 2 3 5 c|
|0 0 2 3 5 c
|8 8 - - 9 9|
|6 7 a - - -|
|6 7 a b b -|
```
```
|- - - 3 4 4|
|1 1 2 3 5 c|
|0 0 2 3 5 c
|8 8 - - 9 9|
|6 7 a - - -|
|6 7 a - b b|
```
```
|- - - - 4 4|
|1 1 2 - 5 c|
|0 0 2 - 5 c
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
```
|4 4 - - - -|
|1 1 2 - 5 c|
|0 0 2 - 5 c
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
```
|4 4 - - 5 -|
|1 1 2 - 5 c|
|0 0 2 - - c
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
```
|4 4 - - 5 c|
|1 1 2 - 5 c|
|0 0 2 - - -
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
```
|4 4 2 - 5 c|
|1 1 2 - 5 c|
|0 0 - - - -
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
```
|4 4 2 - 5 c|
|1 1 2 - 5 c|
|- - - - 0 0
|8 8 - 3 9 9|
|6 7 a 3 - -|
|6 7 a 3 b b|
```
