package io.github.tcompose_date_picker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.extensions.formatLocalDateTime
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKTimePicker(
    modifier: Modifier = Modifier,
    config: ConfigTimePicker = ConfigTimePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    onTimeSelected: (LocalTime?) -> Unit,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    inputFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
    textField: (@Composable (MutableInteractionSource) -> Unit)? = null, // دعم TextField مخصص
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState()
    val interactionSource = remember { MutableInteractionSource() }

    val formattedTime by remember {
        derivedStateOf {
            config.initTime?.formatLocalDateTime(use24HourFormat = config.is24Hour) ?: ""
        }
    }

    // تشغيل التفاعل عند الضغط
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                showTimePicker = true
            }
        }
    }

    isDialogOpen(showTimePicker)
    // ✅ **استخدام `textField` الممرر أو `OutlinedTextField` كافتراضي**
    textField?.invoke(interactionSource) ?: OutlinedTextField(
        modifier = modifier.width(IntrinsicSize.Max),
        shape = shape,
        readOnly = true,
        value = formattedTime,
        label = config.label,
        placeholder = config.placeholder,
        textStyle = config.style,
        enabled = config.enable,
        supportingText = config.supportingText,
        leadingIcon = config.leadingIcon,
        trailingIcon = config.trailingIcon,
        prefix = config.prefix,
        suffix = config.suffix,
        colors = inputFieldColors,
        onValueChange = {},
        interactionSource = interactionSource
    )

    if (showTimePicker) {
        AlertDialog(
            modifier = dialogConfig.modifier,

            onDismissRequest = {
                showTimePicker = false
                isDialogOpen(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected(LocalTime(timeState.hour, timeState.minute))
                    showTimePicker = false
                }) {
                    Text(dialogConfig.buttonOk, style = dialogConfig.textOKStyle)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
                }
            },
            title = { Text(config.title) },
            text = {
                TimePicker(

                    modifier = dialogConfig.timeDialogModifier,
                    state = timeState,
                    colors = colors
                )
            }
        )
    }
}
