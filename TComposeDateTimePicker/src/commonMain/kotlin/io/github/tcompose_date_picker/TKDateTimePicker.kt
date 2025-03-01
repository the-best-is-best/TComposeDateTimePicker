package io.github.tcompose_date_picker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import com.mohamedrejeb.calf.ui.datepicker.rememberAdaptiveDatePickerState
import com.mohamedrejeb.calf.ui.timepicker.rememberAdaptiveTimePickerState
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.dialogs.date_picker.AdaptiveDatePickerDialog
import io.github.tcompose_date_picker.dialogs.time_picker.AdaptiveTimePickerDialog
import io.github.tcompose_date_picker.dialogs.time_picker.TimePickerDialog
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
    useAdaptive: Boolean = false,
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
    onDismissDate: () -> Unit = {},
    onDismissTime: () -> Unit = {},
    enable: Boolean = true

    ) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var tempDate by remember { mutableStateOf<LocalDate?>(null) }
    var tempTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val materialDatePickerState = rememberDatePickerState(
        yearRange = config.dateConfig.yearRange,
        initialSelectedDateMillis = config.dateConfig.initDate?.toEpochMillis()
    )
    val adaptiveDatePickerState = rememberAdaptiveDatePickerState(
        yearRange = config.dateConfig.yearRange,
        initialSelectedDateMillis = config.dateConfig.initDate?.toEpochMillis()
    )

    val materialTimeState = rememberTimePickerState(
        initialHour = config.timeConfig.initTime?.hour ?: LocalTime.now().hour,
        initialMinute = config.timeConfig.initTime?.minute ?: LocalTime.now().minute,
        is24Hour = config.timeConfig.is24Hour
    )

    val adaptiveTimePicker = rememberAdaptiveTimePickerState(
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
        else -> null
    }

    when (textFieldType) {
        is TextFieldType.Custom -> textFieldType.textField(inputModifier)
        TextFieldType.Outlined -> OutlinedTextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedDateTime,
            colors = resolvedColors!!,
            textStyle = config.style,
            enabled = enable,
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
            colors = resolvedColors!!,
            textStyle = config.style,
            enabled = enable,
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
        if (!useAdaptive) {
            io.github.tcompose_date_picker.dialogs.date_picker.DatePickerDialog(
                onDismiss = {
                    showDatePicker = false
                    isDialogOpen(false)
                    onDismissDate()
                },
                dialogConfig = dialogConfig,
                datePickerState = materialDatePickerState,
                onDateSelected = {
                    materialDatePickerState.selectedDateMillis?.let { millis ->
                        tempDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        showDatePicker = false
                        showDatePicker = false
                        showTimePicker = true

                    }
                },
                config = config.dateConfig,
                colors = colorsDate,
            )
        } else {
            AdaptiveDatePickerDialog(
                onDismiss = {
                    showDatePicker = false
                    isDialogOpen(false)
                    onDismissDate()
                },
                dialogConfig = dialogConfig,
                datePickerState = adaptiveDatePickerState,
                onDateSelected = {
                    adaptiveDatePickerState.selectedDateMillis?.let { millis ->
                        tempDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        showDatePicker = false
                        showTimePicker = true
                    }
                },
                config = config.dateConfig,
                colors = colorsDate,
            )
        }
    }

    if (showTimePicker) {
        if (!useAdaptive) {
            TimePickerDialog(
                dialogConfig = dialogConfig,
                timePickerState = materialTimeState,
                onDismiss = {
                    showTimePicker = false
                    isDialogOpen(false)
                    onDismissTime()
                },
                onDateSelected = {
                    tempDate?.let { date ->
                        tempTime = materialTimeState.hour to materialTimeState.minute
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
                },
                config = config.timeConfig,
                colors = colorsTime
            )
        } else {
            AdaptiveTimePickerDialog(
                dialogConfig = dialogConfig,
                timePickerState = adaptiveTimePicker,
                onDismiss = {
                    showTimePicker = false
                    isDialogOpen(false)
                    onDismissTime()
                },
                onDateSelected = {
                    tempDate?.let { date ->
                        tempTime = adaptiveTimePicker.hour to adaptiveTimePicker.minute
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

                },
                config = config.timeConfig,
                colors = colorsTime
            )
        }
    }
}
