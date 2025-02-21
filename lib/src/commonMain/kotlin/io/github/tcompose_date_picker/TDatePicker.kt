package io.tbib.tcomposedatepicker

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import io.github.tcompose_date_picker.MyDatePicker
import io.github.tcompose_date_picker.MyDateTimePicker
import io.github.tcompose_date_picker.MyTimePicker
import io.tbib.tcomposedatepicker.configs.ConfigButtonDialog
import io.tbib.tcomposedatepicker.configs.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.tbib.tcomposedatepicker.configs.ConfigTimePicker
import io.tbib.tcomposedatepicker.states.DatePickerState
import io.tbib.tcomposedatepicker.states.DateTimePickerState
import io.tbib.tcomposedatepicker.states.TimePickerState
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
            state: DatePickerState,
            configButtonDialog:  ConfigButtonDialog = ConfigButtonDialog(textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 1.25.sp
            )),
            onDateSelected:(LocalDate?)->Unit,
            colors : DatePickerColors = DatePickerDefaults.colors(),
            inputFieldColors: TextFieldColors =  TextFieldDefaults.colors(),
            shape: CornerBasedShape = MaterialTheme.shapes.small
        ) {
            MyDatePicker(modifier, config,state,configButtonDialog, onDateSelected, colors, inputFieldColors, shape)
        }
        @Composable
        fun ShowTimePicker(
            modifier: Modifier = Modifier,
            config: ConfigTimePicker = ConfigTimePicker(),
            configButtonDialog: ConfigButtonDialog = ConfigButtonDialog(textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 1.25.sp
            )),
            state: TimePickerState,
            onTimeSelected: (LocalTime?) -> Unit,
            colors: TimePickerColors = TimePickerDefaults.colors(),
            inputFieldColors: TextFieldColors =  TextFieldDefaults.colors(),
            shape: CornerBasedShape = MaterialTheme.shapes.small


        ) {
           MyTimePicker(
                modifier,
                config = config,
                state = state,
                onTimeSelected = onTimeSelected,
                configButtonDialog = configButtonDialog,
               colors = colors,
                inputFieldColors = inputFieldColors,
               shape = shape
           )
        }
        @Composable
        fun ShowDateTimePicker(
            modifier: Modifier = Modifier,
            config: ConfigDateTimePicker = ConfigDateTimePicker(),
            configButtonDialog:  ConfigButtonDialog = ConfigButtonDialog(textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 1.25.sp
            )),
            state: DateTimePickerState,
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
                state = state,
                configButtonDialog = configButtonDialog,
                colorsDate = colorsDate,
                colorsTime = colorsTime,
                inputFieldColors = inputFieldColors,
                shape = shape
            )
        }
    }

}