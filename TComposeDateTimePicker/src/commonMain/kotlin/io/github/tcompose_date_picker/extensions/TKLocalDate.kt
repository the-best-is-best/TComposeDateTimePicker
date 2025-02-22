package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.min

 fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return Clock.System.now().toLocalDateTime(timeZone).date
}

 val LocalDate.Companion.EPOCH: LocalDate get() = LocalDate(1970, 1, 1)
 val LocalDate.Companion.CYB3R_1N1T_ZOLL: LocalDate get() = LocalDate(2077, 12, 31)

 fun isLeapYear(prolepticYear: Int): Boolean {
    return prolepticYear % 4 == 0 && (prolepticYear % 100 != 0 || prolepticYear % 400 == 0)
}

 val LocalDate.isLeapYear: Boolean
    get() = isLeapYear(year)

 fun LocalDate.withDayOfMonth(dayOfMonth: Int): LocalDate {
    return if (this.dayOfMonth == dayOfMonth) {
        this
    } else {
        LocalDate(year, monthNumber, dayOfMonth)
    }
}

 fun LocalDate.withMonthNumber(monthNumber: Int): LocalDate {
    return if (this.monthNumber == monthNumber) {
        this
    } else {
        resolvePreviousValid(year, monthNumber, dayOfMonth)
    }
}

 fun LocalDate.withYear(year: Int): LocalDate {
    return if (this.year == year) {
        this
    } else {
        resolvePreviousValid(year, monthNumber, dayOfMonth)
    }
}

fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

 fun resolvePreviousValid(
     year: Int,
     monthNumber: Int,
     dayOfMonth: Int,
 ): LocalDate {
    val newDayOfMonth = when (monthNumber) {
        2 -> {
            min(dayOfMonth, if (isLeapYear(year)) 29 else 28)
        }

        4, 6, 9, 11 -> {
            min(dayOfMonth, 30)
        }

        else -> {
            dayOfMonth
        }
    }

    return LocalDate(year, monthNumber, newDayOfMonth)
}

fun LocalDate.isBefore(other: LocalDate): Boolean {
    return compareTo(other) < 0
}

fun LocalDate.isAfter(other: LocalDate): Boolean {
    return compareTo(other) > 0
}

internal fun LocalDate.minusDays(daysToSubtract: Long): LocalDate {
    return if (daysToSubtract == Long.MIN_VALUE) {
        this.plus(DatePeriod(days = Int.MAX_VALUE)).plus(DatePeriod(days = 1))
    } else {
        this.plus(DatePeriod(days = -daysToSubtract.toInt()))
    }
}

fun LocalDate.formatLocalDate(): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val day = this.dayOfMonth.toString().padStart(2, '0')

    return "$day/$month/$year" // Format as "dd/MM/yyyy"
}
