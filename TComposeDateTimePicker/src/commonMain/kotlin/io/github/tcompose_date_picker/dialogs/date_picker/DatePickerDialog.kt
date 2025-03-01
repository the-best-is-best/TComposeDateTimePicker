package io.github.tcompose_date_picker.dialogs.date_picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: () -> Unit,
    datePickerState: DatePickerState,
    config: ConfigDatePicker,
    dialogConfig: ConfigDialog,
    colors: DatePickerColors


) {
    androidx.compose.material3.DatePickerDialog(
        modifier = dialogConfig.modifier,
        onDismissRequest = onDismiss,

        confirmButton = {
            TextButton(onClick = {
                onDateSelected()

            }) {
                Text(dialogConfig.buttonOk, style = dialogConfig.textOKStyle)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
            }
        },
        content = {

            DatePicker(
                modifier = dialogConfig.dateDialogModifier,
                title = dialogConfig.title,
                headline = dialogConfig.headline,
                dateFormatter = config.dateFormatter
                    ?: remember { DatePickerDefaults.dateFormatter() },
                state = datePickerState,
                colors = colors,
                showModeToggle = true
            )

        }

    )
}