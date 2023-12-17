package day12

import println
import readInput

fun processRow(row: String): Pair<CharArray, List<Int>> {
  val splits = row.split(" ")
  val pattern = splits[0].toCharArray()
  val ans = splits[1].split(",").map { it.toInt() }
  return Pair(pattern, ans)
}

fun calcLine(row: String): Long {
  val (pattern, ans) = processRow(row)
  return dpSolve(pattern, ans)
}

fun calcLine2(row: String): Long {
  val (pattern, ans) = processRow(row)
  val str = pattern.joinToString("")
  val pattern2 = listOf(str, str, str, str, str).joinToString("?").toCharArray()
  val ans2 = ans + ans + ans + ans + ans

  return dpSolve(pattern2, ans2)
}

fun dpSolve(pattern_: CharArray, ans: List<Int>): Long {
  val pattern = (pattern_.toList() + listOf('.')).toCharArray()
  val maxAns = ans.max() + 1
  val table: List<List<MutableList<Long>>> =
      pattern.map { i ->
        (ans + listOf(0)).map { j -> mutableListOf(*(LongArray(maxAns).toTypedArray())) }
      }

  for (i in table.indices) {
    for (j in table[i].indices) {
      for (k in table[i][j].indices) {
        val c = pattern[i]
        // start by building i = 0
        if (i == 0) {
          // we're currently on the first ans - everything else is a zero
          if (j != 0) table[i][j][k] = 0
          else if (c == '.') {
            if (k == 0) table[i][j][k] = 1 else table[i][j][k] = 0
          } else if (c == '#') {
            if (k == 1) table[i][j][k] = 1 else table[i][j][k] = 0
          } else { // c == '?'
            if (k == 0 || k == 1) table[i][j][k] = 1 else table[i][j][k] = 0
          }
        } else {

          // now we extend from i = 0
          // we run a computation for # and for .
          // ? is simply a sum of both computes
          var score = 0.toLong()

          if (c != '#') { // c == '.' or '?'
            // we add the previous i, j, k=0 to the correct termination of the previous j (k ==
            // ans[j])
            if (k == 0) {
              score += table[i - 1][j][0]
              if (j > 0) score += table[i - 1][j - 1][ans[j - 1]]
            }
          }

          if (c != '.') { // c == '#' or '?'
            // we'll extend the previous k-1
            if (k > 0) score += table[i - 1][j][k - 1]
          }

          table[i][j][k] = score
        }
      }
    }
  }

  return table.last().last().first() // i = last, j = last (imaginary 0), k = 0 (no hashes hanging)
}

fun part1(input: List<String>): Long {
  var total = 0.toLong()
  for (row in input) {
    total += calcLine(row)
  }
  return total
}

fun part2(input: List<String>): Long {
  var total = 0.toLong()
  for (row in input) {
    total += calcLine2(row)
  }
  return total
}

fun main() {
  val input = readInput("Day12")
  part1(input).println()
  part2(input).println()
}
