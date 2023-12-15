package day09

import println
import readInput

fun makeRow(row: String): List<Int> {
  return row.split(" ").map { it.toInt() }
}

fun getNextInSequence(row: List<Int>): Int {
  if (row.all { it == row[0] }) {
    return row[0]
  }

  val newRow = row.dropLast(1).mapIndexed { i, v -> row[i + 1] - v }

  return row.last() + getNextInSequence(newRow)
}

fun getPrevInSequence(row: List<Int>): Int {
  if (row.all { it == row[0] }) {
    return row[0]
  }

  val newRow = row.dropLast(1).mapIndexed { i, v -> row[i + 1] - v }

  return row.first() - getPrevInSequence(newRow)
}

fun part1(input: List<String>): Int {
  var totals = 0
  for (row in input) {
    totals += getNextInSequence(makeRow(row))
  }

  return totals
}

fun part2(input: List<String>): Int {
  var totals = 0
  for (row in input) {
    totals += getPrevInSequence(makeRow(row))
  }

  return totals
}

fun main() {
  val input = readInput("Day09")
  part1(input).println()
  part2(input).println()
}
