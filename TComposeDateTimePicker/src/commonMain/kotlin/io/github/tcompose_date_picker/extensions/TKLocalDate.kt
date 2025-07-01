package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.min
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
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


@Deprecated(
    message = "Use withDay instead",
    replaceWith = ReplaceWith("withDay(day)")
)
fun LocalDate.withDayOfMonth(dayOfMonth: Int): LocalDate {
    return if (this.day == dayOfMonth) {
        this
    } else {
        resolvePreviousValid(year, month.number, dayOfMonth)
    }
}

fun LocalDate.withDay(day: Int): LocalDate {
    return if (this.day == day) {
        this
    } else {
        resolvePreviousValid(year, month.number, day)
    }
}

@Deprecated(
    message = "Use withMonth instead",
    replaceWith = ReplaceWith("withMonth(month)")
)
fun LocalDate.withMonthNumber(monthNumber: Int): LocalDate {
    return if (this.monthNumber == monthNumber) {
        this
    } else {
        resolvePreviousValid(year, monthNumber, dayOfMonth)
    }
}

fun LocalDate.withMonth(month: Int): LocalDate {
    return if (this.month.number == month) {
        this
    } else {
        resolvePreviousValid(year, month, day)
    }
}


 fun LocalDate.withYear(year: Int): LocalDate {
    return if (this.year == year) {
        this
    } else {
        resolvePreviousValid(year, month.number, day)
    }
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDayIn(timeZone = TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

internal fun resolvePreviousValid(
     year: Int,
     month: Int,
     day: Int,
 ): LocalDate {
    val newDayOfMonth = when (month) {
        2 -> {
            min(day, if (isLeapYear(year)) 29 else 28)
        }

        4, 6, 9, 11 -> {
            min(day, 30)
        }

        else -> {
            month
        }
    }

    return LocalDate(year, month, newDayOfMonth)
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
    val month = month.number.toString().padStart(2, '0')
    val day = day.toString().padStart(2, '0')

    return "$day/$month/$year" // Format as "dd/MM/yyyy"
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toInstant(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant =
    this.atStartOfDayIn(timeZone)


@OptIn(ExperimentalTime::class)
fun LocalDate.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this.atStartOfDayIn(timeZone).toLocalDateTime(timeZone)
}