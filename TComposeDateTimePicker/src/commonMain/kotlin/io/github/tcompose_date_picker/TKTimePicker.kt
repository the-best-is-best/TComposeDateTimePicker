package io.github.tcompose_date_picker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import io.github.tbib.kadaptiveui.time_picker.rememberAdaptiveTimePickerState
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.dialogs.time_picker.AdaptiveTimePickerDialog
import io.github.tcompose_date_picker.dialogs.time_picker.TimePickerDialog
import io.github.tcompose_date_picker.extensions.formatLocalTime
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKTimePicker(
    useAdaptive: Boolean = false,
    modifier: Modifier = Modifier,
    config: ConfigTimePicker = ConfigTimePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    onTimeSelected: (LocalTime?) -> Unit,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    inputFieldColors: TextFieldColors? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
    textFieldType: TextFieldType = TextFieldType.Outlined,
    onDismiss: () -> Unit = {},
    enable: Boolean = true,
) {
    var showTimePicker by remember { mutableStateOf(false) }

    val materialTimeState = rememberTimePickerState(
        initialHour = config.initTime?.hour ?: 0,
        initialMinute = config.initTime?.minute ?: 0,
        is24Hour = config.is24Hour
    )

    val adaptiveTimeState = rememberAdaptiveTimePickerState(
        initialHour = config.initTime?.hour ?: 0,
        initialMinute = config.initTime?.minute ?: 0,
        is24Hour = config.is24Hour
    )

    // هذا ال state لتخزين الوقت المختار مؤقتاً ويُحدّث يدويًا
    var tempTime by remember { mutableStateOf(config.initTime ?: LocalTime(0, 0)) }

    // عندما يتغير initTime في config يحدث tempTime و materialTimeState
    LaunchedEffect(config.initTime) {
        config.initTime?.let {
            materialTimeState.hour = it.hour
            materialTimeState.minute = it.minute
            tempTime = it
        }
    }

    // نص الوقت المنسق للعرض
    val formattedTime by remember(tempTime, config.is24Hour) {
        derivedStateOf {
            tempTime.formatLocalTime(use24HourFormat = config.is24Hour)
        }
    }

    // تحديث الحالة إذا تم فتح أو إغلاق الـ Dialog
    LaunchedEffect(showTimePicker) {
        isDialogOpen(showTimePicker)
    }

    // ضبط ألوان الـ TextField بناءً على النوع
    val resolvedColors = when (textFieldType) {
        TextFieldType.Outlined -> inputFieldColors ?: OutlinedTextFieldDefaults.colors()
        TextFieldType.Filled -> inputFieldColors ?: TextFieldDefaults.colors()
        else -> null
    }

    val inputModifier = modifier
        .width(IntrinsicSize.Max)
        .pointerInput(formattedTime) {
            awaitEachGesture {
                awaitFirstDown(pass = PointerEventPass.Initial)
                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                if (upEvent != null) {
                    showTimePicker = true
                }
            }
        }

    // رسم TextField بناءً على textFieldType
    when (textFieldType) {
        is TextFieldType.Custom -> textFieldType.textField(inputModifier)

        TextFieldType.Outlined -> OutlinedTextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedTime,
            label = config.label,
            placeholder = config.placeholder,
            textStyle = config.style,
            enabled = enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            colors = resolvedColors!!,
            onValueChange = {},
        )

        TextFieldType.Filled -> TextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedTime,
            label = config.label,
            placeholder = config.placeholder,
            textStyle = config.style,
            enabled = enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            colors = resolvedColors!!,
            onValueChange = {},
        )
    }

    // عرض الـ Dialog المناسب بناءً على useAdaptive
    if (showTimePicker) {
        if (!useAdaptive) {
            TimePickerDialog(
                dialogConfig = dialogConfig,
                timePickerState = materialTimeState,
                onDismiss = {
                    showTimePicker = false
                    isDialogOpen(false)
                    onDismiss()
                },
                onDateSelected = {
                    val selected = LocalTime(materialTimeState.hour, materialTimeState.minute)
                    tempTime = selected
                    onTimeSelected(selected)
                    showTimePicker = false
                },
                config = config,
                colors = colors
            )
        } else {
            AdaptiveTimePickerDialog(
                dialogConfig = dialogConfig,
                timePickerState = adaptiveTimeState,
                onDismiss = {
                    showTimePicker = false
                    isDialogOpen(false)
                    onDismiss()
                },
                onDateSelected = {
                    val selected = LocalTime(adaptiveTimeState.hour, adaptiveTimeState.minute)
                    tempTime = selected
                    onTimeSelected(selected)
                    showTimePicker = false
                },
                config = config,
                colors = colors
            )
        }
    }
}
