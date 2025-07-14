package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.min
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

val LocalDateTime.Companion.EPOCH: LocalDateTime
    get() = LocalDateTime(LocalDate(1970, 1, 1), LocalTime(0, 0))

val LocalDateTime.Companion.CYB3R_1N1T_ZOLL: LocalDateTime
    get() = LocalDateTime(LocalDate(2099, 12, 31), LocalTime(23, 59, 59))

fun LocalDateTime.with(date: LocalDate, time: LocalTime): LocalDateTime {
    return if (this.date == date && this.time == time) this else LocalDateTime(date, time)
}

fun LocalDateTime.withYear(year: Int): LocalDateTime {
    return with(date.withYear(year), time)
}

@Deprecated("Use withMonth instead", ReplaceWith("this.withMonth(month)"))
fun LocalDateTime.withMonthNumber(monthNumber: Int): LocalDateTime {
    return if (this.monthNumber == monthNumber) this else resolvePreviousValid(
        year,
        monthNumber,
        day
    )
}

fun LocalDateTime.withMonth(month: Int): LocalDateTime {
    return with(date.withMonth(month), time)
}

@Deprecated("Use withDay instead", ReplaceWith("this.withDay(day)"))
fun LocalDateTime.withDayOfMonth(dayOfMonth: Int): LocalDateTime {
    return with(date.withDayOfMonth(dayOfMonth), time)
}

fun LocalDateTime.withDay(day: Int): LocalDateTime {
    return with(date.withDay(day), time)
}

fun LocalDateTime.withHour(hour: Int): LocalDateTime {
    return with(date, time.withHour(hour))
}

fun LocalDateTime.withMinute(minute: Int): LocalDateTime {
    return with(date, time.withMinute(minute))
}

fun LocalDateTime.isBefore(other: LocalDateTime): Boolean {
    return compareTo(other) < 0
}

fun LocalDateTime.isAfter(other: LocalDateTime): Boolean {
    return compareTo(other) > 0
}

fun LocalDateTime.formatLocalDateTime(
    withoutSeconds: Boolean = true,
    use24HourFormat: Boolean = false
): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.month.number.toString().padStart(2, '0')
    val day = this.day.toString().padStart(2, '0')
    val hour12 = this.hour % 12
    val displayHour = if (hour12 == 0) 12 else hour12
    val hour =
        if (use24HourFormat) this.hour.toString().padStart(2, '0') else displayHour.toString()
    val amPm = if (use24HourFormat) "" else if (this.hour < 12) " AM" else " PM"
    val minute = this.minute.toString().padStart(2, '0')
    val second = if (withoutSeconds) "" else ":${this.second.toString().padStart(2, '0')}"
    return "$day/$month/$year $hour:$minute$second$amPm"
}

fun LocalDateTime.toIsoString(): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.month.number.toString().padStart(2, '0')
    val day = this.day.toString().padStart(2, '0')
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    val second = this.second.toString().padStart(2, '0')
    val nano = this.nanosecond.toString().padStart(9, '0').trimEnd('0')

    return if (nano.isNotEmpty()) {
        "$year-$month-$day" + "T$hour:$minute:$second.$nano"
    } else {
        "$year-$month-$day" + "T$hour:$minute:$second"
    }
}


@OptIn(ExperimentalTime::class)
fun LocalDateTime.toInstant(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    return this.toInstant(timeZone)
}


@OptIn(ExperimentalTime::class)
fun LocalDateTime.toIsoStringWithOffset(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val instant = this.toInstant(timeZone)
    val offset = timeZone.offsetAt(instant)
    val offsetHours = offset.totalSeconds / 3600
    val offsetMinutes = (offset.totalSeconds % 3600) / 60
    val offsetSign = if (offset.totalSeconds >= 0) "+" else "-"
    val formattedOffset = offsetSign + abs(offsetHours).toString().padStart(2, '0') + ":" + abs(
        offsetMinutes
    ).toString().padStart(2, '0')
    return "${this.toIsoString()}$formattedOffset"
}

fun LocalDateTime.formattedDateTimeWithDayName(
    withoutSeconds: Boolean = true,
    use24HourFormat: Boolean = false,
): String {
    val dayName = this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = this.year.toString().padStart(4, '0')
    val month = this.month.number.toString().padStart(2, '0')
    val day = this.day.toString().padStart(2, '0')
    val hour12 = this.hour % 12
    val displayHour = if (hour12 == 0) 12 else hour12
    val hour =
        if (use24HourFormat) this.hour.toString().padStart(2, '0') else displayHour.toString()
    val amPm = if (use24HourFormat) "" else if (this.hour < 12) " AM" else " PM"
    val minute = this.minute.toString().padStart(2, '0')
    val second = if (withoutSeconds) "" else ":${this.second.toString().padStart(2, '0')}"
    return "$dayName $day/$month/$year $hour:$minute$second$amPm"
}

@OptIn(ExperimentalTime::class)
fun LocalTime.withCurrentDateAndOffset(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val now = Clock.System.now()
    val currentDate = now.toLocalDateTime(timeZone).date
    val offset = timeZone.offsetAt(now)
    val timeWithSeconds =
        this.toString().let { if (it.count { c -> c == ':' } == 1) "$it:00" else it }
    return "$currentDate" + "T$timeWithSeconds$offset"
}

private fun resolvePreviousValid(year: Int, month: Int, day: Int): LocalDateTime {
    val newDayOfMonth = when (month) {
        2 -> min(day, if (isLeapYear(year)) 29 else 28)
        4, 6, 9, 11 -> min(day, 30)
        else -> min(day, 31)
    }
    return LocalDateTime(year, month, newDayOfMonth, 0, 0)
}

