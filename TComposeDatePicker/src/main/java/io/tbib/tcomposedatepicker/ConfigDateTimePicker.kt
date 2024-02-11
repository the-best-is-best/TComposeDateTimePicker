package io.tbib.tcomposedatepicker

import androidx.compose.runtime.Composable
import java.time.LocalDateTime

class ConfigDateTimePicker(
    val dateConfig: ConfigDatePicker = ConfigDatePicker(),
    val timeConfig: ConfigTimePicker = ConfigTimePicker(),
    val displayInitDateTime: LocalDateTime? = null,
    val  label : @Composable ()->Unit = {},
    val    placeholder : @Composable ()->Unit = {},
    ) {

}