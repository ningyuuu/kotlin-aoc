package day06

// import kotlinx.coroutine.runBlocking

import println
import readInput

fun getRaces(input: List<String>): List<Pair<Long, Long>> {
  val times = input[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
  val dists = input[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }

  return times.mapIndexed { i, time -> Pair(time, dists[i]) }
}

fun getSingleRace(input: List<String>): Pair<Long, Long> {
  val time = input[0].split("\\s+".toRegex()).drop(1).joinToString("").toLong()
  val dist = input[1].split("\\s+".toRegex()).drop(1).joinToString("").toLong()

  return Pair(time, dist)
}

fun getCombis(time: Long, dist: Long): Long {
  "looking at time: $time, dist: $dist".println()
  var ceiling = time
  var floor = 0.toLong()

  while (ceiling != floor) {
    "ceiling: $ceiling, floor: $floor".println()
    val half = (ceiling + floor) / 2

    if (half * (time - half) > dist) {
      ceiling = half
    } else {
      if (half == floor) {
        floor++
      } else {
        floor = half
      }
    }
  }

  "final answer: $ceiling".println()

  var counts = ((time / 2) - ceiling + 1) * 2
  if (time % 2 == 0.toLong()) {
    counts--
  }

  return counts
}

fun part1(input: List<String>): Long {
  var total = 1.toLong()
  val races = getRaces(input)
  for (race in races) {
    total *= getCombis(race.first, race.second)
  }

  return total
}

fun part2(input: List<String>): Long {
  val race = getSingleRace(input)
  return getCombis(race.first, race.second)
}

fun main() {
  val input = readInput("Day06")
  part1(input).println()
  part2(input).println()
}
