fun getFirstLastNumber(input: String): Int {
    var i = 0
    var first = 0
    var second = 0
    while (i < input.length) {
        if (input[i].isDigit()) {
            first = input[i].toString().toInt()
            break
        }
        i += 1
    }

    i = input.length - 1

    while (i >= 0) {
        if (input[i].isDigit()) {
            second = input[i].toString().toInt()
            break
        }
        i -= 1
    }

    return first * 10 + second
}


fun getFirstLastNumberOrSpell(input: String): Int {
    val spellList = listOf(
        Pair("zero", 0),
        Pair("one", 1),
        Pair("two", 2),
        Pair("three", 3),
        Pair("four", 4),
        Pair("five", 5),
        Pair("six", 6),
        Pair("seven", 7),
        Pair("eight", 8),
        Pair("nine", 9),
    )

    var i = 0
    var first = 0
    var second = 0
    while (first == 0 && i < input.length) {
        if (input[i].isDigit()) {
            first = input[i].toString().toInt()
            break
        }

        for (spellPair in spellList) {
            val textLen = spellPair.first.length
            if (i + textLen < input.length) {
                if (input.subSequence(i, i + textLen).toString() == spellPair.first) {
                    first = spellPair.second
                }
            }
        }

        i += 1
    }

    i = input.length - 1

    while (second == 0 && i >= 0) {
        if (input[i].isDigit()) {
            second = input[i].toString().toInt()
            break
        }

        for (spellPair in spellList) {
            val textLen = spellPair.first.length
            if (i - textLen >= 0) {
                if (input.subSequence(i - textLen + 1, i + 1).toString() == spellPair.first) {
                    second = spellPair.second
                }
            }
        }

        i -= 1
    }
    (first * 10 + second).println()
    return first * 10 + second
}

fun main() {
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
            total += getFirstLastNumberOrSpell(line)
        }
        return total
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
