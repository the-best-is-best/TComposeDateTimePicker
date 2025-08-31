package io.github.tcompose_date_picker.dialogs.date_picker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import io.github.tbib.kadaptiveui.date_picker.AdaptiveDatePicker
import io.github.tbib.kadaptiveui.date_picker.AdaptiveDatePickerState
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: () -> Unit,
    datePickerState: AdaptiveDatePickerState,
    config: ConfigDatePicker,
    dialogConfig: ConfigDialog,
    colors: DatePickerColors
) {
    androidx.compose.material3.DatePickerDialog(
        modifier = dialogConfig.modifier.padding(8.dp),
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
            AdaptiveDatePicker(
                dateFormatter = config.dateFormatter
                    ?: remember { DatePickerDefaults.dateFormatter() },
                title = dialogConfig.title,
                headline = dialogConfig.headline,
                state = datePickerState,
                colors = colors,
            )
        }
    )
}