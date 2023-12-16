package day10

import kotlin.system.exitProcess
import println
import readInput

data class Coord(val x: Int, val y: Int)

fun findS(map: List<String>): Coord {
  for (i in map.indices) {
    for (j in map[i].indices) {
      if (map[i][j] == 'S') {
        return Coord(j, i)
      }
    }
  }

  return Coord(-1, -1)
}

fun replaceSWithPipe(map: List<String>, s: Coord): List<String> {
  val top: Boolean
  val bot: Boolean
  val left: Boolean
  val right: Boolean

  // first determine which of the directions are pointing
  if (s.x == 0) {
    left = false
  } else {
    left = listOf('-', 'L', 'F').contains(map[s.y][s.x - 1])
  }

  if (s.x == (map[0].length - 1)) {
    right = false
  } else {
    right = listOf('-', 'J', '7').contains(map[s.y][s.x + 1])
  }

  if (s.y == 0) {
    top = false
  } else {
    top = listOf('|', '7', 'F').contains(map[s.y - 1][s.x])
  }

  if (s.y == (map.size - 1)) {
    bot = false
  } else {
    bot = listOf('|', 'L', 'J').contains(map[s.y + 1][s.x])
  }

  // then determine actual pipe char
  val char: Char

  if (top && bot) {
    char = '|'
  } else if (top && left) {
    char = 'J'
  } else if (top && right) {
    char = 'L'
  } else if (left && right) {
    char = '-'
  } else if (left && bot) {
    char = '7'
  } else if (right && bot) {
    char = 'J'
  } else {
    println("This should not happen!")
    exitProcess(1)
  }

  // dumb way to replace
  val mutable: MutableList<String> = mutableListOf(*map.toTypedArray())
  mutable[s.y] = map[s.y].substring(0, s.x) + char.toString() + map[s.y].substring(s.x + 1)

  return mutable.toList()
}

fun buildVisitedMap(map: List<String>): MutableList<MutableList<Int>> {
  val mutable: MutableList<MutableList<Int>> = mutableListOf()
  for (row in map) {
    mutable.add(row.split("").map { -1 }.toMutableList())
  }

  return mutable
}

fun buildDenoiseMap(map: List<String>, traverse: MutableList<MutableList<Int>>): List<List<Char>> {
  val denoiseMap =
      map.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> if (traverse[y][x] >= 0) map[y][x] else '.' }
      }
  return denoiseMap
}

fun traverse(map: List<String>, start: Coord): MutableList<MutableList<Int>> {
  val visited = buildVisitedMap(map)
  val queue = ArrayDeque<Coord>()

  visited[start.y][start.x] = 0
  queue.addLast(start)

  while (queue.size > 0) {
    val curr = queue.removeFirst()
    val num = visited[curr.y][curr.x]
    val char = map[curr.y][curr.x]

    when (char) {
      'L' -> {
        if (visited[curr.y - 1][curr.x] < 0) {
          visited[curr.y - 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y - 1))
        }
        if (visited[curr.y][curr.x + 1] < 0) {
          visited[curr.y][curr.x + 1] = num + 1
          queue.add(Coord(curr.x + 1, curr.y))
        }
      }
      'J' -> {
        if (visited[curr.y - 1][curr.x] < 0) {
          visited[curr.y - 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y - 1))
        }
        if (visited[curr.y][curr.x - 1] < 0) {
          visited[curr.y][curr.x - 1] = num + 1
          queue.add(Coord(curr.x - 1, curr.y))
        }
      }
      'F' -> {
        if (visited[curr.y + 1][curr.x] < 0) {
          visited[curr.y + 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y + 1))
        }
        if (visited[curr.y][curr.x + 1] < 0) {
          visited[curr.y][curr.x + 1] = num + 1
          queue.add(Coord(curr.x + 1, curr.y))
        }
      }
      '7' -> {
        if (visited[curr.y + 1][curr.x] < 0) {
          visited[curr.y + 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y + 1))
        }
        if (visited[curr.y][curr.x - 1] < 0) {
          visited[curr.y][curr.x - 1] = num + 1
          queue.add(Coord(curr.x - 1, curr.y))
        }
      }
      '|' -> {
        if (visited[curr.y + 1][curr.x] < 0) {
          visited[curr.y + 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y + 1))
        }
        if (visited[curr.y - 1][curr.x] < 0) {
          visited[curr.y - 1][curr.x] = num + 1
          queue.add(Coord(curr.x, curr.y - 1))
        }
      }
      '-' -> {
        if (visited[curr.y][curr.x + 1] < 0) {
          visited[curr.y][curr.x + 1] = num + 1
          queue.add(Coord(curr.x + 1, curr.y))
        }
        if (visited[curr.y][curr.x - 1] < 0) {
          visited[curr.y][curr.x - 1] = num + 1
          queue.add(Coord(curr.x - 1, curr.y))
        }
      }
    }
  }

  return visited
}

fun buildBlownUpMap(denoiseMap: List<List<Char>>): Array<CharArray> {
  val blownUpMap = Array(denoiseMap.size * 2 - 1) { CharArray(denoiseMap[0].size * 2 - 1) { '.' } }

  denoiseMap.forEachIndexed { y, chars ->
    chars.forEachIndexed { x, c -> if (c != '.') blownUpMap[y * 2][x * 2] = c }
  }

  val vertUpper = listOf('7', 'F', '|')
  val vertLower = listOf('L', 'J', '|')
  val horiLeft = listOf('L', 'F', '-')
  val horiRight = listOf('J', '7', '-')

  blownUpMap.forEachIndexed { y, chars ->
    chars.forEachIndexed { x, c ->
      if (y % 2 == 1 && x % 2 == 0) {
        if (vertUpper.contains(blownUpMap[y - 1][x]) && vertLower.contains(blownUpMap[y + 1][x])) {
          blownUpMap[y][x] = '|'
        }
      }

      if (x % 2 == 1 && y % 2 == 0) {
        if (horiLeft.contains(blownUpMap[y][x - 1]) && horiRight.contains(blownUpMap[y][x + 1])) {
          blownUpMap[y][x] = '-'
        }
      }
    }
  }

  return blownUpMap
}

fun floodFill(c: Coord, map: Array<CharArray>, visited: Char = 'X') {
  if (map[c.y][c.x] != '.') return

  val queue = ArrayDeque(listOf(c))

  while (queue.size > 0) {
    val next = queue.removeFirst()

    if (map[next.y][next.x] != '.') continue

    map[next.y][next.x] = visited

    if (next.y > 0) {
      queue.addLast(Coord(next.x, next.y - 1))
    }
    if (next.y < map.size - 1) {
      queue.addLast(Coord(next.x, next.y + 1))
    }
    if (next.x > 0) {
      queue.addLast(Coord(next.x - 1, next.y))
    }
    if (next.x < map[0].size - 1) {
      queue.addLast(Coord(next.x + 1, next.y))
    }
  }
}

fun part1(input: List<String>): Int {
  val coordS = findS(input)
  val mapWithoutS = replaceSWithPipe(input, coordS)

  return traverse(mapWithoutS, coordS).maxOf { it.max() }
}

fun part2(input: List<String>): Int {
  val coordS = findS(input)
  val mapWithoutS = replaceSWithPipe(input, coordS)

  // this is the initial map of traversals with numbers, as seen in the example
  val traverseMap = traverse(mapWithoutS, coordS)

  // this is a map where we see the char only if it's been traversed.
  // otherwise it's just noise so we ignore and print '.'
  val denoiseMap = buildDenoiseMap(mapWithoutS, traverseMap)

  // and now, we create a 2x length blown up map, joining up the walls along the way
  val blownUpMap = buildBlownUpMap(denoiseMap)

  // then, we use a flood fill from all edges to remove spaces outside the fence
  for (i in blownUpMap[0].indices) {
    floodFill(Coord(i, 0), blownUpMap)
    floodFill(Coord(i, blownUpMap[0].size - 1), blownUpMap)
  }

  for (j in blownUpMap.indices) {
    floodFill(Coord(0, j), blownUpMap)
    floodFill(Coord(blownUpMap.size - 1, j), blownUpMap)
  }

  // finally, we compute the number of points that's not traversed nor flooded
  var count = 0
  for (i in blownUpMap.indices step 2) {
    for (j in blownUpMap[0].indices step 2) {
      if (blownUpMap[i][j] == '.') count++
    }
  }

  return count
}

fun main() {
  val input = readInput("Day10")
  part1(input).println()
  part2(input).println()
}
