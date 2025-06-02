package io.github.tcompose_date_picker.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate =
    this.toLocalDateTime(timeZone).date


fun Instant.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this.toLocalDateTime(timeZone)
}