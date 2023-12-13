package day01

import println
import readInput

fun getFirstLastNumber(input: String): Int {
  val first = input.first { it.isDigit() }
  val second = input.last { it.isDigit() }

  return "$first$second".toInt()
}

val spellList =
    mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

fun getFirstLastNumberSpelled(input: String): Int {
  val inputSpelled =
      input
          .mapIndexed { index, c ->
            if (c.isDigit()) c
            else
                input.possibleWordsAt(index).firstNotNullOfOrNull { candidate ->
                  spellList[candidate]
                }
          }
          .filterNotNull()
          .joinToString()
  val first = inputSpelled.first { it.isDigit() }
  val second = inputSpelled.last { it.isDigit() }

  return "$first$second".toInt()
}

fun String.possibleWordsAt(startIdx: Int): List<String> {
  return (3..5).map { len -> substring(startIdx, (startIdx + len).coerceAtMost(length)) }
}

fun part1(input: List<String>): Int {
  var total = 0
  for (line in input) {
    total += getFirstLastNumber(line)
  }
  return total
}

fun part2(input: List<String>): Int {
  var total = 0
  for (line in input) {
    total += getFirstLastNumberSpelled(line)
  }
  return total
}

fun main() {
  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
