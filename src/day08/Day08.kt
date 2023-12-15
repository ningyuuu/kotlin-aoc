package day08

import println
import readInput

fun buildSimpleMap(rows: List<String>): Map<String, Pair<String, String>> {
  val locs: MutableMap<String, Pair<String, String>> = mutableMapOf()

  for (row in rows) {
    val firstSplit = row.split(" = ")
    val secondSplit = firstSplit[1].split(", ")
    locs[firstSplit[0]] = Pair(secondSplit[0].drop(1), secondSplit[1].dropLast(1))
  }

  return locs
}

fun findLCM(a: Long, b: Long): Long {
  val larger = if (a > b) a else b
  val maxLcm = a * b
  var lcm = larger
  while (lcm <= maxLcm) {
    if (lcm % a == 0.toLong() && lcm % b == 0.toLong()) {
      return lcm
    }
    lcm += larger
  }
  return maxLcm
}

fun findLCMList(numbers: List<Int>): Long {
  var result = numbers[0].toLong()
  for (i in 1 until numbers.size) {
    result = findLCM(result, numbers[i].toLong())
  }
  return result
}

fun part1(input: List<String>): Int {
  val simpleMap = buildSimpleMap(input.drop(2))
  val moves = input[0]
  var curr = "AAA"
  var steps = 0

  while (curr != "ZZZ") {
    for (c in moves) {
      steps++
      if (c == 'L') {
        curr = simpleMap[curr]!!.first
      } else if (c == 'R') {
        curr = simpleMap[curr]!!.second
      }
    }
  }
  return steps
}

fun part2(input: List<String>): Long {
  val simpleMap = buildSimpleMap(input.drop(2))
  val moves = input[0]
  val curr: MutableMap<String, String> = mutableMapOf()
  val steps: MutableMap<String, Int> = mutableMapOf()
  val done: MutableMap<String, Boolean> = mutableMapOf()

  for (k in simpleMap.keys) {
    if (k[2] == 'A') {
      curr[k] = k
      steps[k] = 0
      done[k] = false
    }
  }
  println(done.keys.joinToString(", "))

  while (!done.values.all { it }) {
    for (c in moves) {
      for (d in done) {
        if (!d.value) {
          steps[d.key] = steps[d.key]!! + 1

          if (c == 'L') curr[d.key] = simpleMap[curr[d.key]]!!.first
          else if (c == 'R') curr[d.key] = simpleMap[curr[d.key]]!!.second

          if (curr[d.key]!!.last() == 'Z') {
            done[d.key] = true
            "${d.key} is done".println()
          }
        }
      }
    }
  }

  steps.values.joinToString(", ").println()

  return findLCMList(steps.values.toList())
}

fun main() {
  val input = readInput("Day08")
  part1(input).println()
  part2(input).println()
}
