package day02

import kotlin.math.max
import println
import readInput

data class Combi(var red: Int, var green: Int, var blue: Int)

fun getGameIdAndCombis(line: String): Pair<Int, ArrayList<Combi>> {
  val lineSplit = line.split(": ")
  val id = lineSplit[0].split(" ")[1].toInt()
  val combis = ArrayList<Combi>()
  val combiStrings = lineSplit[1].split("; ")
  for (cs in combiStrings) {
    var red = 0
    var green = 0
    var blue = 0

    for (c in cs) {
      val colors = cs.split(", ")
      for (color in colors) {
        val colorSplit = color.split(" ")
        if (colorSplit[1] == "green") {
          green = colorSplit[0].toInt()
        } else if (colorSplit[1] == "red") {
          red = colorSplit[0].toInt()
        } else if (colorSplit[1] == "blue") {
          blue = colorSplit[0].toInt()
        } else {
          color.println()
          "!!!".println()
        }
      }
    }

    combis.add(Combi(red, green, blue))
  }

  return Pair(id, combis)
}

fun getGameIdIfLegalCubes(line: String): Int {
  val redLimit = 12
  val greenLimit = 13
  val blueLimit = 14
  val idCombiPair = getGameIdAndCombis(line)

  for (combi in idCombiPair.second) {
    if (combi.red > redLimit || combi.blue > blueLimit || combi.green > greenLimit) {
      return 0
    }
  }

  return idCombiPair.first
}

fun getCubesPower(line: String): Int {
  var redPower = 0
  var greenPower = 0
  var bluePower = 0
  val idCombiPair = getGameIdAndCombis(line)

  for (combi in idCombiPair.second) {
    redPower = max(redPower, combi.red)
    greenPower = max(greenPower, combi.green)
    bluePower = max(bluePower, combi.blue)
  }

  return redPower * greenPower * bluePower
}

fun main() {
  fun part1(input: List<String>): Int {
    var total = 0
    for (line in input) {
      total += getGameIdIfLegalCubes(line)
    }
    return total
  }

  fun part2(input: List<String>): Int {
    var total = 0
    for (line in input) {
      total += getCubesPower(line)
    }
    return total
  }

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
