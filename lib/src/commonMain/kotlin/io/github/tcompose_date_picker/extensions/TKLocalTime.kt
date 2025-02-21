package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

 fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return Clock.System.now().toLocalDateTime(timeZone).time
}

 val LocalTime.Companion.MIN: LocalTime get() = LocalTime(0, 0, 0, 0)
 val LocalTime.Companion.MAX: LocalTime get() = LocalTime(23, 59, 59, 999_999_999)

 fun LocalTime.withMinute(minute: Int): LocalTime {
    return if (this.minute == minute) {
        this
    } else {
        LocalTime(hour, minute, second, nanosecond)
    }
}

 fun LocalTime.withHour(hour: Int): LocalTime {
    return if (this.hour == hour) {
        this
    } else {
        LocalTime(hour, minute, second, nanosecond)
    }
}

 fun LocalTime.isBefore(other: LocalTime): Boolean {
    return compareTo(other) < 0
}

 fun LocalTime.isAfter(other: LocalTime): Boolean {
    return compareTo(other) > 0
}


fun LocalTime.formatLocalDateTime(
    withoutSeconds: Boolean = true,
    use24HourFormat: Boolean = false
): String {

    val hour = if (use24HourFormat) this.hour.toString().padStart(2, '0')
    else (if (this.hour == 0) 12 else this.hour % 12).toString()

    val amPm = if (use24HourFormat) "" else if (this.hour < 12) " AM" else " PM"
    val minute = this.minute.toString().padStart(2, '0')
    val second = if (withoutSeconds) "" else ":${this.second.toString().padStart(2, '0')}"

    return "$hour:$minute$second$amPm"
}
