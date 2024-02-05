package io.tbib.tcomposedatepicker

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyDatePicker(config: ConfigDatePicker, onDateSelected:(String)->Unit){

    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = config.displayMode,
        initialSelectedDateMillis  = config.initialSelectedDateMillis,
        initialDisplayedMonthMillis = config.initialDisplayedMonthMillis,
        yearRange = config.yearRange,
        selectableDates = config.selectableDates
    )
    val selectedDate = datePickerState.selectedDateMillis?.let {
        TDatePicker.convertMillisToDate(it)
    } ?: ""
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },


                confirmButton = {
                    Button(onClick = {
                        onDateSelected(selectedDate)
                        showDialog = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    ElevatedButton(onClick = {
                        showDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
    ElevatedButton(onClick = { showDialog = true }) {
        Text(text = "Show Date Picker")
    }
}