package day11

import Coord
import kotlin.math.max
import kotlin.math.min
import println
import readInput

fun blowUp(grid: List<List<Char>>): Pair<List<Int>, List<Int>> {
  val cols: MutableList<Int> = mutableListOf()
  val rows: MutableList<Int> = mutableListOf()

  for (i in grid.indices) {
    if (grid[i].all { it == '.' }) {
      rows.add(i)
    }
  }

  for (i in grid[0].indices) {
    if (grid.map { it[i] }.all { it == '.' }) {
      cols.add(i)
    }
  }

  return Pair(rows.toList(), cols.toList())
}

fun getCoords(grid: List<List<Char>>): List<Coord> {
  val coords: MutableList<Coord> = mutableListOf()

  for (y in grid.indices) {
    for (x in grid[y].indices) {
      if (grid[y][x] == '#') {
        coords.add(Coord(x, y))
      }
    }
  }

  return coords.toList()
}

fun computeDist(a: Coord, b: Coord, rows: List<Int>, cols: List<Int>, blowDist: Int = 2): Long {
  // since it's a manhattan distance, we can simply add x and y
  var dist: Long = 0

  val diff: Long = blowDist.toLong() - 1

  val smallX = min(a.x, b.x)
  val bigX = max(a.x, b.x)
  dist += bigX - smallX
  for (i in smallX..bigX) {
    if (cols.contains(i)) {
      dist += diff
    }
  }

  val smallY = min(a.y, b.y)
  val bigY = max(a.y, b.y)
  dist += bigY - smallY
  for (i in smallY..bigY) {
    if (rows.contains(i)) {
      dist += diff
    }
  }

  return dist
}

fun part1(input: List<String>): Long {
  val grid = input.map { it.toCharArray().toList() }
  val (rows, cols) = blowUp(grid)
  val coords = getCoords(grid)

  return coords.sumOf { coords.sumOf { other -> computeDist(it, other, rows, cols) } } / 2
}

fun part2(input: List<String>): Long {
  val grid = input.map { it.toCharArray().toList() }
  val (rows, cols) = blowUp(grid)
  val coords = getCoords(grid)

  return coords.sumOf { coords.sumOf { other -> computeDist(it, other, rows, cols, 1000000) } } / 2
}

fun main() {
  val input = readInput("Day11")

  part1(input).println()
  part2(input).println()
}
