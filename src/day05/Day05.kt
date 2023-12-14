package day05

// import kotlinx.coroutine.runBlocking
import java.lang.Long.min
import println
import readInput

data class Day5Dataset(
    val seeds: List<Long>,
    val seedToSoil: Day5Map,
    val soilToFert: Day5Map,
    val fertToWater: Day5Map,
    val waterToLight: Day5Map,
    val lightToTemp: Day5Map,
    val tempToHumid: Day5Map,
    val humidToLoc: Day5Map,
)

data class MapRange(val start: Long, val end: Long, val offset: Long)

class Day5Map(rows: List<String>) {
  private val ranges = ArrayList<MapRange>()

  init {
    for (row in rows) {
      val rowSplits = row.split(" ")
      val destStart = rowSplits[0].toLong()
      val sourceStart = rowSplits[1].toLong()
      val offset = destStart - sourceStart

      val rangeEnd = rowSplits[2].toLong()
      val sourceEnd = sourceStart + rangeEnd - 1

      ranges.add(MapRange(sourceStart, sourceEnd, offset))
    }
  }

  fun convert(source: Long): Long {
    for (r in ranges) {
      if (r.start <= source && r.end >= source) {
        return source + r.offset
      }
    }
    return source
  }
}

fun buildDataset(input: List<String>): Day5Dataset {
  val seeds = input[0].split(" ").drop(1).map { it.toLong() }

  var i = 3
  var start = i
  while (input[i] != "") {
    i++
  }
  val seedToSoil = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (input[i] != "") {
    i++
  }
  val soilToFert = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (input[i] != "") {
    i++
  }
  val fertToWater = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (input[i] != "") {
    i++
  }
  val waterToLight = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (input[i] != "") {
    i++
  }
  val lightToTemp = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (input[i] != "") {
    i++
  }
  val tempToHumid = Day5Map(input.subList(start, i))

  i += 2
  start = i
  while (i < input.size) {
    i++
  }
  val humidToLoc = Day5Map(input.subList(start, i))

  return Day5Dataset(
      seeds,
      seedToSoil,
      soilToFert,
      fertToWater,
      waterToLight,
      lightToTemp,
      tempToHumid,
      humidToLoc)
}

fun part1(input: List<String>): Long {
  var min = 9999999999999999
  val data = buildDataset(input)

  for (seed in data.seeds) {
    val soil = data.seedToSoil.convert(seed)
    val fert = data.soilToFert.convert(soil)
    val water = data.fertToWater.convert(fert)
    val light = data.waterToLight.convert(water)
    val temp = data.lightToTemp.convert(light)
    val humid = data.tempToHumid.convert(temp)
    val loc = data.humidToLoc.convert(humid)

    min = min(min, loc)
  }

  return min
}

fun part2(input: List<String>): Long {
  val data = buildDataset(input)

  var i = 0
  val seeds = ArrayList<Pair<Long, Long>>()
  while (i < data.seeds.size) {
    seeds.add(Pair(data.seeds[i], data.seeds[i + 1]))
    i += 2
  }

  //  runBlocking {
  val localMins =
      seeds.map {
        println("running " + it.first.toString() + " to " + (it.first + it.second - 1).toString())
        var localMin = 99999999999999
        for (seed in it.first ..< it.first + it.second) {
          val soil = data.seedToSoil.convert(seed)
          val fert = data.soilToFert.convert(soil)
          val water = data.fertToWater.convert(fert)
          val light = data.waterToLight.convert(water)
          val temp = data.lightToTemp.convert(light)
          val humid = data.tempToHumid.convert(temp)
          val loc = data.humidToLoc.convert(humid)
          localMin = min(localMin, loc)
        }

        localMin
      }
  //  }

  return localMins.min()
}

fun main() {
  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}
