package io.tbib.tcomposedatepicker

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
  class TDatePicker {

    companion object {
        @SuppressLint("RememberReturnType")
        @Composable
        fun ShowDatePicker(config: ConfigDatePicker, onDateSelected:(String)->Unit) {
            MyDatePicker(config, onDateSelected)
        }

        @SuppressLint("SimpleDateFormat")
      internal  fun convertMillisToDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            return formatter.format(Date(millis))
        }

        @Composable
        fun TimePicker() {
            println("Showing time picker")
        }
        @Composable
        fun DateTimePicker() {
            println("Showing date time picker")
        }
    }

}