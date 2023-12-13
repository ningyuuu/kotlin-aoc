package day03

import kotlin.math.max
import kotlin.math.min
import println
import readInput

data class NumberCoords(val num: Int, val row: Int, val start: Int, val end: Int)

data class GearCoords(val row: Int, val col: Int)

fun buildInputCharMatrix(input: List<String>): List<List<Char>> {
  return input.map { it.toList() }
}

fun buildNumCoordList(matrix: List<List<Char>>): List<NumberCoords> {
  val numCoords = ArrayList<NumberCoords>()

  for (i in matrix.indices) {
    val row = matrix[i]
    var start = -1
    for (j in row.indices) {
      if (row[j].isDigit()) {
        if (start == -1) {
          start = j
        }
      } else {
        if (start != -1) {
          val num = row.subList(start, j).joinToString("").toInt()
          numCoords.add(NumberCoords(num, i, start, j - 1))
          start = -1
        }
      }
    }

    if (start != -1) {
      val num = row.subList(start, row.size).joinToString("").toInt()
      numCoords.add(NumberCoords(num, i, start, row.size - 1))
    }
  }

  return numCoords
}

fun isNumCoordLegal(nc: NumberCoords, matrix: List<List<Char>>): Boolean {
  val symbols = listOf('@', '#', '$', '%', '&', '*', '+', '-', '=', '/')

  val xMin = max(0, nc.start - 1)
  val xMax = min(matrix[0].size - 1, nc.end + 1)

  val yMin = max(0, nc.row - 1)
  val yMax = min(matrix.size - 1, nc.row + 1)

  for (i in yMin..yMax) {
    for (j in xMin..xMax) {
      if (symbols.contains(matrix[i][j])) {
        return true
      }
    }
  }

  return false
}

fun buildGearList(matrix: List<List<Char>>): List<GearCoords> {
  val gearCoords = ArrayList<GearCoords>()

  for (i in matrix.indices) {
    for (j in matrix[i].indices) {
      if (matrix[i][j] == '*') {
        gearCoords.add(GearCoords(i, j))
      }
    }
  }

  return gearCoords
}

fun isAroundGear(num: NumberCoords, gear: GearCoords): Boolean {
  if (num.row < gear.row - 1 || num.row > gear.row + 1) {
    return false
  }

  if (num.start > gear.col + 1 || num.end < gear.col - 1) {
    return false
  }

  return true
}

fun getValuesForGear(gear: GearCoords, numbers: List<NumberCoords>): Int {
  val validNumbers = numbers.filter { isAroundGear(it, gear) }

  if (validNumbers.size == 2) {
    return validNumbers[0].num * validNumbers[1].num
  }

  return 0
}

fun main() {
  fun part1(input: List<String>): Int {
    val inputCharMatrix = buildInputCharMatrix(input)
    val numCoordList = buildNumCoordList(inputCharMatrix)

    var total = 0
    for (nc in numCoordList) {
      if (isNumCoordLegal(nc, inputCharMatrix)) {
        total += nc.num
      }
    }

    return total
  }

  fun part2(input: List<String>): Int {

    val inputCharMatrix = buildInputCharMatrix(input)
    val numCoordList = buildNumCoordList(inputCharMatrix)
    val gearList = buildGearList(inputCharMatrix)

    var total = 0
    for (g in gearList) {
      total += getValuesForGear(g, numCoordList)
    }

    return total
  }

  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
