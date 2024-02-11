package io.tbib.tcomposedatepicker

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        fun ShowDatePicker(
            modifier: Modifier = Modifier,
            config: ConfigDatePicker,
            onDateSelected:(LocalDate?)->Unit ,
            colors : DatePickerColors = DatePickerDefaults.colors(),
            inputFieldColors: TextFieldColors =  TextFieldDefaults.colors(),
            shape: CornerBasedShape = MaterialTheme.shapes.small
        ) {
            MyDatePicker(modifier, config, onDateSelected, colors, inputFieldColors, shape)
        }
        @Composable
        fun ShowTimePicker(
            modifier: Modifier = Modifier,
            config: ConfigTimePicker = ConfigTimePicker(),
            onTimeSelected: (LocalTime?) -> Unit,
             colors: TimePickerColors = TimePickerDefaults.colors(),
            inputFieldColors: TextFieldColors =  TextFieldDefaults.colors(),
            shape: CornerBasedShape = MaterialTheme.shapes.small


        ) {
           MyTimePicker(
                modifier,
                config = config,
                onTimeSelected = onTimeSelected,
               colors,
                inputFieldColors,
               shape
           )
        }
        @Composable
        fun ShowDateTimePicker(
            modifier: Modifier = Modifier,

            config:ConfigDateTimePicker = ConfigDateTimePicker(),
            onDateTimeSelected: (LocalDateTime?) -> Unit,
            colorsDate: DatePickerColors = DatePickerDefaults.colors(),
            colorsTime: TimePickerColors = TimePickerDefaults.colors(),
            inputFieldColors: TextFieldColors =  TextFieldDefaults.colors(),
            shape: CornerBasedShape = MaterialTheme.shapes.small

        ){
            MyDateTimePicker(
                modifier = modifier,
                onDateTimeSelected = onDateTimeSelected,
                config = config,
                colorsDate = colorsDate,
                colorsTime = colorsTime,
                inputFieldColors = inputFieldColors,
                shape = shape
            )
        }
    }

}