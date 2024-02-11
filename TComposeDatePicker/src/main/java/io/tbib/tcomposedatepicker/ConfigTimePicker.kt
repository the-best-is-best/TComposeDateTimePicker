package io.tbib.tcomposedatepicker

import androidx.compose.runtime.Composable
import java.time.LocalTime

class ConfigTimePicker(
    val timeFormatPattern: String = "hh:mm a",
    val initTime: LocalTime= LocalTime.NOON,
    val displayInitTime:LocalTime ?= null,
    val title: String = "Select a time",
    val is24Hour: Boolean = false,
    val timeRange : ClosedRange<LocalTime> = LocalTime.MIN..LocalTime.MAX,
    val  label : @Composable ()->Unit = {},
    val    placeholder : @Composable ()->Unit = {},

    )