package io.github.tcompose_date_picker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.config.TextFieldType
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
    inputFieldColors: TextFieldColors? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
    textField: (@Composable (Modifier) -> Unit)? = null,
    textFieldType: TextFieldType = TextFieldType.Outlined
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = config.initTime?.hour ?: 0,
        initialMinute = config.initTime?.minute ?: 0,
        is24Hour = config.is24Hour
    )
    var tempTime by remember {
        mutableStateOf(config.initTime?.let { it.hour to it.minute })
    }
    val formattedTime by remember {
        derivedStateOf {
            if (tempTime == null) "" else LocalTime(
                tempTime!!.first,
                tempTime!!.second
            ).formatLocalDateTime(use24HourFormat = config.is24Hour)
        }
    }

    isDialogOpen(showTimePicker)

    val resolvedColors = when (textFieldType) {
        TextFieldType.Outlined -> inputFieldColors ?: OutlinedTextFieldDefaults.colors()
        TextFieldType.Filled -> inputFieldColors ?: TextFieldDefaults.colors()
    }

    textField?.invoke(modifier.width(IntrinsicSize.Max).pointerInput(formattedTime) {
        awaitEachGesture {
            awaitFirstDown(pass = PointerEventPass.Initial)
            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
            if (upEvent != null) {
                showTimePicker = true
            }
        }
    }) ?: when (textFieldType) {
        TextFieldType.Outlined -> OutlinedTextField(
            modifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedTime) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showTimePicker = true
                    }
                }
            },
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
            colors = resolvedColors,
            onValueChange = {},
        )

        TextFieldType.Filled -> TextField(
            modifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedTime) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showTimePicker = true
                    }
                }
            },
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
            colors = resolvedColors,
            onValueChange = {},
        )
    }

    if (showTimePicker) {
        AlertDialog(
            modifier = dialogConfig.modifier,
            onDismissRequest = {
                showTimePicker = false
                isDialogOpen(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    tempTime = Pair(timeState.hour, timeState.minute)
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