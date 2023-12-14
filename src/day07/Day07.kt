package day07

import println
import readInput

val cardOrder = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
val cardJokerOrder = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

enum class HandType {
  FIVE,
  FOUR,
  FULL,
  THREE,
  TWO_PAIR,
  PAIR,
  HIGH
}

val typeOrder =
    listOf(
        HandType.FIVE,
        HandType.FOUR,
        HandType.FULL,
        HandType.THREE,
        HandType.TWO_PAIR,
        HandType.PAIR,
        HandType.HIGH)

open class Hand(val hand: String, val bid: Int) : Comparable<Hand> {
  open val type = getType(hand)

  fun getType(hand: String): HandType {
    val cardMap: MutableMap<Char, Int> = mutableMapOf()

    for (c in hand) {
      if (cardMap.contains(c)) {
        cardMap[c] = cardMap[c]!! + 1
      } else {
        cardMap[c] = 1
      }
    }

    if (cardMap.size == 1) {
      return HandType.FIVE
    }

    if (cardMap.size == 2) {
      if (cardMap.values.max() == 4) {
        return HandType.FOUR
      }

      return HandType.FULL
    }

    if (cardMap.size == 3) {
      if (cardMap.values.max() == 3) {
        return HandType.THREE // 3 1 1
      }

      return HandType.TWO_PAIR // 2 2 1
    }

    if (cardMap.size == 4) {
      return HandType.PAIR
    }

    return HandType.HIGH
  }

  fun typeCompare(a: HandType, b: HandType): Int {
    if (a == b) {
      return 0
    }

    return typeOrder.indexOf(a) - typeOrder.indexOf(b)
  }

  open fun cardCompare(a: String, b: String): Int {
    var i = 0
    while (i < a.length) {
      if (a[i] == b[i]) {
        i++
        continue
      }

      return cardOrder.indexOf(a[i]) - cardOrder.indexOf(b[i])
    }
    return 0
  }

  override fun compareTo(other: Hand): Int {
    val typeCompareResult = typeCompare(this.type, other.type)
    if (typeCompareResult != 0) {
      return typeCompareResult
    }
    return cardCompare(this.hand, other.hand)
  }
}

class JokerHand(hand: String, bid: Int) : Hand(hand, bid) {
  override val type: HandType = getJokerType(hand)

  fun getJokerType(hand: String): HandType {
    val cardMap: MutableMap<Char, Int> = mutableMapOf()
    var jCount = 0

    for (c in hand) {
      if (c == 'J') {
        jCount++
      } else if (cardMap.contains(c)) {
        cardMap[c] = cardMap[c]!! + 1
      } else {
        cardMap[c] = 1
      }
    }

    if (cardMap.size <= 1) {
      return HandType.FIVE // J J J J J would be 0 now
    }

    if (cardMap.size == 2) {
      if (cardMap.values.max() + jCount == 4) {
        return HandType.FOUR
      }

      return HandType.FULL
    }

    if (cardMap.size == 3) {
      if (cardMap.values.max() + jCount == 3) {
        return HandType.THREE // 3 1 1
      }

      return HandType.TWO_PAIR // 2 2 1
    }

    if (cardMap.size == 4) {
      return HandType.PAIR
    }

    return HandType.HIGH
  }

  override fun cardCompare(a: String, b: String): Int {
    var i = 0
    while (i < a.length) {
      if (a[i] == b[i]) {
        i++
        continue
      }

      return cardJokerOrder.indexOf(a[i]) - cardJokerOrder.indexOf(b[i])
    }
    return 0
  }
}

fun buildHand(row: String): Hand {
  val rowSplit = row.split(" ")
  return Hand(rowSplit[0], rowSplit[1].toInt())
}

fun buildJokerHand(row: String): JokerHand {
  val rowSplit = row.split(" ")
  return JokerHand(rowSplit[0], rowSplit[1].toInt())
}

fun part1(input: List<String>): Long {
  val hands = input.map { buildHand(it) }.sorted()
  var winnings = 0.toLong()
  for (i in hands.indices) {
    winnings += hands[i].bid * (hands.size - i)
  }
  return winnings
}

fun part2(input: List<String>): Long {
  val hands = input.map { buildJokerHand(it) }.sorted()
  var winnings = 0.toLong()
  for (i in hands.indices) {
    winnings += hands[i].bid * (hands.size - i)
  }
  return winnings
}

fun main() {
  val input = readInput("Day07")
  part1(input).println()
  part2(input).println()
}
