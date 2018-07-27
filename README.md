# UnblockMe Solver written in Kotlin


## About the game
UnblockMe is a puzzle game from Kira Games

A sample puzzle looks like this
![Puzzle 96 from Easy Original package](https://github.com/wayneyu/unblockMeSolver/blob/master/doc/Screenshot_20180726-203231_resized.jpg)

The rules of the game are fairly simple:
Each block, including the red block, can only be moved in the direction of the length of the block, i.e. a horizontal block moves horizontally and vertical block vertically.
A block can be moved into any empty space that another block does not occupy.
The objective of the game is unblock the red block and get it pass the right edge of the board.

Fairly simple rules, but it can be fairly challenging, especially trying to solve a puzzle within the optimal number of moves, which in this case is 15.


## The solver

### Setup
This solver parses a board in text format and represents it by the board sizes and a list of `Bar`:
```
data class Board(val xSize: Int,
                 val ySize: Int,
                 val bars: List<Bar>)
```

A `Bar` is represented by the left most or top most tile of the bar and its length and direction.
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
This puzzle can be solved using Djikstra's shortest path algorithm.
It's



### Result
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
