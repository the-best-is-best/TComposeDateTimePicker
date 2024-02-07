package io.tbib.tcomposedatepicker

import java.time.LocalTime

class ConfigTimePicker(
    val timeFormatPattern: String = "hh:mm a",
    val initTime: LocalTime = LocalTime.now(),
    val title: String = "Select a time",
    val is24Hour: Boolean = false,
    val timeRange : ClosedRange<LocalTime> = LocalTime.MIN..LocalTime.MAX,

    )