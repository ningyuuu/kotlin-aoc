package day14

import Grid
import println
import readInput

class Frame(input: List<String>) : Grid(input) {
  fun collapseNorth() {
    val charGrid = data.map { it.toCharArray() }
    for (col in charGrid[0].indices) {
      var os = 0
      var idx = charGrid.size - 1
      while (idx >= 0) {
        if (charGrid[idx][col] == 'O') {
          os++
          charGrid[idx][col] = '.'
        }

        if (charGrid[idx][col] == '#') {
          while (os > 0) {
            charGrid[idx + os][col] = 'O'
            os--
          }
        }

        idx--
      }

      while (os > 0) {
        charGrid[idx + os][col] = 'O'
        os--
      }
    }

    data = charGrid.map { it.toList() }
  }

  fun collapseSouth() {
    data = data.reversed()
    collapseNorth()
    data = data.reversed()
  }

  fun collapseEast() {
    val charGrid = data.map { it.toCharArray() }
    for (row in charGrid) {
      var os = 0
      var idx = 0
      while (idx < row.size) {
        if (row[idx] == 'O') {
          os++
          row[idx] = '.'
        }

        if (row[idx] == '#') {
          while (os > 0) {
            row[idx - os] = 'O'
            os--
          }
        }

        idx++
      }

      while (os > 0) {
        row[idx - os] = 'O'
        os--
      }
    }

    data = charGrid.map { it.toList() }
  }

  fun collapseWest() {
    data = data.map { it.reversed() }
    collapseEast()
    data = data.map { it.reversed() }
  }

  fun computeLoad(): Int {
    return data.mapIndexed { i, row -> row.filter { it == 'O' }.size * (data.size - i) }.sum()
  }
}

fun part1(input: List<String>): Int {
  val frame = Frame(input)
  //  frame.data.map { it.joinToString("").println() }
  //  print("\n\n")
  frame.collapseNorth()
  //  frame.data.map { it.joinToString("").println() }
  return frame.computeLoad()
}

fun part2(input: List<String>): Int {
  val frame = Frame(input)
  val results: MutableList<Int> = mutableListOf()
  for (i in 1..500) {
    frame.collapseNorth()
    frame.collapseWest()
    frame.collapseSouth()
    frame.collapseEast()
    results.add(frame.computeLoad())
  }

  // compute offset - when did pattern first repeat
  var i = 0
  var j = 0
  var found = false
  while (!found && i < results.size) {
    j = 5 // arbitrary min repeat sequence length
    while (!found && i + i + j + 1 + j < results.size) {
      println("${results.subList(i, i + j)} vs ${results.subList(i + j + 1, i + j + 1 + j)}")
      if (results.subList(i, i + j) == results.subList(i + j + 1, i + j + j + 1)) {
        found = true
      }
      j++
    }
    i++
  }

  "i: $i, j: $j".println()

  val target = ((1000000000 - i).toLong() % j) + i
  return results[target.toInt() - 1]
}

fun main() {
  //  val input = readInput("test")
  val input = readInput("Day14")
  part1(input).println()
  part2(input).println()
}
