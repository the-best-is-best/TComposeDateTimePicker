package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
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

fun isLeapYear(prolepticYear: Int): Boolean {
    return prolepticYear % 4 == 0 && (prolepticYear % 100 != 0 || prolepticYear % 400 == 0)
}

val LocalDate.isLeapYear: Boolean
    get() = isLeapYear(year)


fun LocalDate.withDay(day: Int): LocalDate {
    return if (this.day == day) this else resolvePreviousValid(year, month.number, day)
}


fun LocalDate.withMonth(month: Int): LocalDate {
    return if (this.month.number == month) this else resolvePreviousValid(year, month, day)
}

fun LocalDate.withYear(year: Int): LocalDate {
    return if (this.year == year) this else resolvePreviousValid(year, month.number, day)
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toEpochMillisAtUtc(): Long {
    return this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}

private fun resolvePreviousValid(year: Int, month: Int, day: Int): LocalDate {
    val newDayOfMonth = when (month) {
        2 -> min(day, if (isLeapYear(year)) 29 else 28)
        4, 6, 9, 11 -> min(day, 30)
        else -> min(day, 31)
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
    return "$day/$month/$year"
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toInstant(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    return this.atStartOfDayIn(timeZone)
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(this, LocalTime(0, 0))
}
