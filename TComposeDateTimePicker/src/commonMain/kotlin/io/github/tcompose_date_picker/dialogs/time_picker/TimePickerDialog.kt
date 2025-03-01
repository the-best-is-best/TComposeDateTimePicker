package io.github.tcompose_date_picker.dialogs.time_picker

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.ConfigTimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: () -> Unit,
    timePickerState: TimePickerState,
    config: ConfigTimePicker,
    dialogConfig: ConfigDialog,
    colors: TimePickerColors
) {
    AlertDialog(
        modifier = dialogConfig.modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDateSelected) {
                Text(dialogConfig.buttonOk, style = dialogConfig.textOKStyle)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
            }
        },
        title = { Text(config.title) },
        text = {
            TimePicker(
                modifier = dialogConfig.timeDialogModifier,
                state = timePickerState,
                colors = colors
            )
        }
    )
}