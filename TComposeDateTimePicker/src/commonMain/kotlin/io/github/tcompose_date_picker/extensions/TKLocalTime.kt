package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return kotlin.time.Clock.System.now().toLocalDateTime(timeZone).time
}

val LocalTime.Companion.MIN: LocalTime
    get() = LocalTime(0, 0, 0, 0)

val LocalTime.Companion.MAX: LocalTime
    get() = LocalTime(23, 59, 59, 999_999_999)

fun LocalTime.withMinute(minute: Int): LocalTime {
    return if (this.minute == minute) {
        this
    } else {
        LocalTime(this.hour, minute, this.second, this.nanosecond)
    }
}

fun LocalTime.withHour(hour: Int): LocalTime {
    return if (this.hour == hour) {
        this
    } else {
        LocalTime(hour, this.minute, this.second, this.nanosecond)
    }
}

fun LocalTime.isBefore(other: LocalTime): Boolean {
    return this < other
}

fun LocalTime.isAfter(other: LocalTime): Boolean {
    return this > other
}

fun LocalTime.formatLocalTime(
    withoutSeconds: Boolean = true,
    use24HourFormat: Boolean = false
): String {
    val hourStr = if (use24HourFormat) {
        this.hour.toString().padStart(2, '0')
    } else {
        val h = if (this.hour == 0 || this.hour == 12) 12 else this.hour % 12
        h.toString()
    }

    val amPm = if (use24HourFormat) "" else if (this.hour < 12) " AM" else " PM"
    val minuteStr = this.minute.toString().padStart(2, '0')
    val secondStr = if (withoutSeconds) "" else ":${this.second.toString().padStart(2, '0')}"

    return "$hourStr:$minuteStr$secondStr$amPm"
}
