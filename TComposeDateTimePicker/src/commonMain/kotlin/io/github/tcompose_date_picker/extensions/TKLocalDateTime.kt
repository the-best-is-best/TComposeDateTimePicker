package io.github.tcompose_date_picker.extensions


import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.DAYS
import kotlin.time.DurationUnit.HOURS
import kotlin.time.DurationUnit.MICROSECONDS
import kotlin.time.DurationUnit.MILLISECONDS
import kotlin.time.DurationUnit.MINUTES
import kotlin.time.DurationUnit.NANOSECONDS
import kotlin.time.DurationUnit.SECONDS

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

 val LocalDateTime.Companion.EPOCH: LocalDateTime
    get() = LocalDateTime(LocalDate.EPOCH, LocalTime.MIN)

 val LocalDateTime.Companion.CYB3R_1N1T_ZOLL: LocalDateTime
    get() = LocalDateTime(LocalDate.CYB3R_1N1T_ZOLL, LocalTime.MAX)

 fun LocalDateTime.with(date: LocalDate, time: LocalTime): LocalDateTime {
    return if (this.date == date && this.time == time) {
        this
    } else {
        LocalDateTime(date, time)
    }
}

 fun LocalDateTime.withYear(year: Int): LocalDateTime {
    return with(date.withYear(year), time)
}

 fun LocalDateTime.withMonthNumber(monthNumber: Int): LocalDateTime {
    return with(date.withMonthNumber(monthNumber), time)
}

 fun LocalDateTime.withDayOfMonth(dayOfMonth: Int): LocalDateTime {
    return with(date.withDayOfMonth(dayOfMonth), time)
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

 fun LocalDateTime.truncatedTo(unit: DurationUnit): LocalDateTime {
    return when (unit) {
        NANOSECONDS -> this

        MICROSECONDS -> LocalDateTime(
            year,
            month,
            dayOfMonth,
            hour,
            minute,
            second,
            nanosecond / 1000
        )

        MILLISECONDS -> LocalDateTime(
            year,
            month,
            dayOfMonth,
            hour,
            minute,
            second,
            nanosecond / 1000000
        )

        SECONDS -> LocalDateTime(year, month, dayOfMonth, hour, minute, second)
        MINUTES -> LocalDateTime(year, month, dayOfMonth, hour, minute)
        HOURS -> LocalDateTime(year, month, dayOfMonth, hour, 0)
        DAYS -> LocalDateTime(year, month, dayOfMonth, 0, 0)
        else -> throw IllegalArgumentException("The value `else` does not match any of the patterns.")
    }
}
fun LocalDateTime.formatLocalDateTime(
    withoutSeconds: Boolean = true,
    use24HourFormat: Boolean = false
): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val day = this.dayOfMonth.toString().padStart(2, '0')

    val hour = if (use24HourFormat) this.hour.toString().padStart(2, '0')
    else (if (this.hour == 0) 12 else this.hour % 12).toString()

    val amPm = if (use24HourFormat) "" else if (this.hour < 12) " AM" else " PM"
    val minute = this.minute.toString().padStart(2, '0')
    val second = if (withoutSeconds) "" else ":${this.second.toString().padStart(2, '0')}"

    return "$day/$month/$year $hour:$minute$second$amPm"
}
