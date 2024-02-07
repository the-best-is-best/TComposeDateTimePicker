package io.tbib.tcomposedatepicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
  class TDatePicker {

    companion object {
        @Composable
        fun ShowDatePicker(config: ConfigDatePicker, onDateSelected:(LocalDate)->Unit , colors : DatePickerColors = DatePickerDefaults.colors() ) {
            MyDatePicker(config, onDateSelected, colors)
        }
        @Composable
        fun ShowTimePicker(
            config: ConfigTimePicker = ConfigTimePicker(),
            onTimeSelected: (LocalTime) -> Unit,
             colors: TimePickerColors = TimePickerDefaults.colors()

        ) {
           MyTimePicker(
                config = config,
                onTimeSelected = onTimeSelected,
               colors
           )
        }
        @Composable
        fun ShowDateTimePicker(config:ConfigDateTimePicker = ConfigDateTimePicker(),
                           onDateTimeSelected: (LocalDateTime) -> Unit,
                           colorsDate: DatePickerColors = DatePickerDefaults.colors(),
                           colorsTime: TimePickerColors = TimePickerDefaults.colors() ){
            MyDateTimePicker(
                onDateTimeSelected = onDateTimeSelected,
                config = config,
                colorsDate = colorsDate,
                colorsTime = colorsTime
            )
        }
    }

}