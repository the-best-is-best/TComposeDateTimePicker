package io.github.tcompose_date_picker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.rememberDatePickerState
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
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.extensions.formatLocalDateTime
import io.github.tcompose_date_picker.extensions.now
import io.github.tcompose_date_picker.extensions.toEpochMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKDateTimePicker(
    modifier: Modifier = Modifier,
    onDateTimeSelected: (LocalDateTime?) -> Unit,
    config: ConfigDateTimePicker = ConfigDateTimePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    colorsDate: DatePickerColors = DatePickerDefaults.colors(),
    colorsTime: TimePickerColors = TimePickerDefaults.colors(),
    inputFieldColors: TextFieldColors? = null,
    textFieldType: TextFieldType = TextFieldType.Outlined,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var tempDate by remember { mutableStateOf<LocalDate?>(null) }
    var tempTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val materialDatePickerState = rememberDatePickerState(
        yearRange = config.dateConfig.yearRange,
        initialSelectedDateMillis = config.dateConfig.initDate?.toEpochMillis()
    )

    val timePickerState = rememberTimePickerState(
        initialHour = config.timeConfig.initTime?.hour ?: LocalTime.now().hour,
        initialMinute = config.timeConfig.initTime?.minute ?: LocalTime.now().minute,
        is24Hour = config.timeConfig.is24Hour
    )

    val formattedDateTime by remember {
        derivedStateOf {
            tempDate?.let { date ->
                tempTime?.let { (hour, minute) ->
                    LocalDateTime(date.year, date.month, date.dayOfMonth, hour, minute)
                        .formatLocalDateTime(
                            withoutSeconds = true,
                            config.timeConfig.is24Hour
                        )
                }
            } ?: ""
        }
    }

    isDialogOpen(showDatePicker || showTimePicker)

    val inputModifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedDateTime) {
        awaitEachGesture {
            awaitFirstDown(pass = PointerEventPass.Initial)
            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
            if (upEvent != null) {
                showDatePicker = true
            }
        }
    }
    val resolvedColors = when (textFieldType) {
        TextFieldType.Outlined -> inputFieldColors ?: OutlinedTextFieldDefaults.colors()
        TextFieldType.Filled -> inputFieldColors ?: TextFieldDefaults.colors()
    }

    when (textFieldType) {
        TextFieldType.Outlined -> OutlinedTextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedDateTime,
            colors = resolvedColors,
            textStyle = config.style,
            enabled = config.enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            label = config.label,
            placeholder = config.placeholder,
            onValueChange = {},
        )

        TextFieldType.Filled -> TextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedDateTime,
            colors = resolvedColors,
            textStyle = config.style,
            enabled = config.enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            label = config.label,
            placeholder = config.placeholder,
            onValueChange = {},
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            modifier = dialogConfig.modifier,
            onDismissRequest = { showDatePicker = false; isDialogOpen(false) },
            confirmButton = {
                TextButton(onClick = {
                    materialDatePickerState.selectedDateMillis?.let { millis ->
                        tempDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        showDatePicker = false
                        showTimePicker = true
                    }
                }) {
                    Text(dialogConfig.buttonNext, style = dialogConfig.textOKStyle)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false; isDialogOpen(false) }) {
                    Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
                }
            },
            content = {
                DatePicker(
                    title = dialogConfig.title,
                    headline = dialogConfig.headline,
                    modifier = dialogConfig.dateDialogModifier,
                    dateFormatter = config.dateConfig.dateFormatter
                        ?: remember { DatePickerDefaults.dateFormatter() },
                    state = materialDatePickerState,
                    colors = colorsDate,
                    showModeToggle = true
                )
            }
        )
    }

    if (showTimePicker) {
        AlertDialog(
            modifier = dialogConfig.modifier,
            onDismissRequest = { showTimePicker = false; isDialogOpen(false) },
            confirmButton = {
                TextButton(onClick = {
                    tempDate?.let { date ->
                        tempTime = timePickerState.hour to timePickerState.minute
                        onDateTimeSelected(
                            LocalDateTime(
                                date.year,
                                date.month,
                                date.dayOfMonth,
                                tempTime!!.first,
                                tempTime!!.second
                            )
                        )
                        showTimePicker = false
                        isDialogOpen(false)
                    }
                }) {
                    Text(dialogConfig.buttonOk, style = dialogConfig.textOKStyle)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false; isDialogOpen(false) }) {
                    Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
                }
            },
            text = {
                TimePicker(
                    modifier = dialogConfig.timeDialogModifier,
                    state = timePickerState,
                    colors = colorsTime
                )
            }
        )
    }
}
