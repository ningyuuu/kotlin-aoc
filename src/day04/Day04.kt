package day04

import kotlin.math.pow
import println
import readInput

fun getMatches(row: String): Int {
  val splits = row.split(" | ")
  val winners = splits[0].split(": ")[1].trim().split("\\s+".toRegex())
  val candidates = splits[1].trim().split("\\s+".toRegex())
  return candidates.filter { winners.contains(it) }.size
}

fun getMatchScore(row: String): Int {
  return 2.toDouble().pow(getMatches(row) - 1).toInt()
}

fun part1(input: List<String>): Int {
  var total = 0

  for (row in input) {
    total += getMatchScore(row)
  }
  return total
}

fun part2(input: List<String>): Int {
  val cards = IntArray(input.size) { 1 }
  for (i in input.indices) {
    val score = getMatches(input[i])

    for (j in 1..score) {
      if (i + j < input.size) {
        cards[i + j] += cards[i]
      }
    }
  }

  return cards.sum()
}

fun main() {
  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
