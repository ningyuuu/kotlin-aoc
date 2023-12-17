package day13

import Grid
import kotlin.system.exitProcess
import println
import readInput

class Frame(input: List<String>) : Grid(input) {
  fun isHoriSymm(idx: Int): Boolean {
    var i = 0
    while (idx - i >= 0 && idx + i + 1 < data.size) {
      if (data[idx - i] != data[idx + i + 1]) {
        return false
      }
      i++
    }

    return true
  }

  fun isVertSymm(idx: Int): Boolean {
    var i = 0
    while (idx - i >= 0 && idx + i + 1 < data[0].size) {
      for (row in data) {
        if (row[idx - i] != row[idx + i + 1]) {
          return false
        }
      }
      i++
    }

    return true
  }

  fun getHoriSymm(exclude: Int = -1): Int {
    for (i in 0 ..< data.size - 1) {
      if (isHoriSymm(i) && i != exclude - 1) {
        return i + 1
      }
    }

    return -1
  }

  fun getVertSymm(exclude: Int = -1): Int {
    for (i in 0 ..< data[0].size - 1) {
      if (isVertSymm(i) && i != exclude - 1) {
        return i + 1
      }
    }

    return -1
  }
}

fun buildFrames(input: List<String>): List<Frame> {
  val frames: MutableList<Frame> = mutableListOf()
  var start = 0

  for (i in input.indices) {
    if (input[i] == "") {
      frames.add(Frame(input.subList(start, i)))
      start = i + 1
    }
  }

  frames.add(Frame(input.subList(start, input.size)))

  return frames
}

fun flipFrame(frame: Frame, i: Int, j: Int): Frame {
  val newInput =
      frame.data.mapIndexed { ri, r ->
        if (ri != i) r.joinToString("")
        else
            r.mapIndexed { ci, c -> if (ci != j) c else if (c == '#') '.' else '#' }
                .joinToString("")
      }

  return Frame(newInput)
}

fun part1(input: List<String>): Long {
  val frames = buildFrames(input)
  var total = 0.toLong()
  for (frame in frames) {
    val vertSymm = frame.getVertSymm()
    if (vertSymm > 0) {
      total += vertSymm
      println("vert $vertSymm")
      continue
    }

    val horiSymm = frame.getHoriSymm()
    if (horiSymm > 0) {
      total += 100 * horiSymm
      println("hori $horiSymm")
      continue
    }
  }
  return total
}

fun part2(input: List<String>): Long {
  val frames = buildFrames(input)
  var total = 0.toLong()
  for (frame in frames) {
    var done = false

    val oldVertSymm = frame.getVertSymm()
    val oldHoriSymm = frame.getHoriSymm()

    for (i in frame.data.indices) {
      if (done) break
      for (j in frame.data[0].indices) {
        if (done) break
        val flipped = flipFrame(frame, i, j)
        val vertSymm = flipped.getVertSymm(exclude = oldVertSymm)
        if (vertSymm > 0 && vertSymm != oldVertSymm) {
          total += vertSymm
          done = true
          println("$i, $j - vert $vertSymm")
          continue
        }

        val horiSymm = flipped.getHoriSymm(exclude = oldHoriSymm)
        if (horiSymm > 0 && horiSymm != oldHoriSymm) {
          total += 100 * horiSymm
          done = true
          println("$i, $j - hori $horiSymm")
          continue
        }
      }
    }

    if (!done) {
      "failed (old hori: $oldHoriSymm, old vert: $oldVertSymm)".println()
      flipFrame(frame, 7, 11).isVertSymm(11).println()
      frame.data.map { it.joinToString("").println() }
      exitProcess(1)
    }
  }
  return total
}

fun main() {
  val input = readInput("Day13")
  //  val input = readInput("test")
  part1(input).println()
  part2(input).println()
}
