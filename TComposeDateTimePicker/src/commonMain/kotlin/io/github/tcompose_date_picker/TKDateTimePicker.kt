package io.github.tcompose_date_picker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
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
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveDatePicker
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.rememberCupertinoDatePickerState
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.extensions.formatLocalDateTime
import io.github.tcompose_date_picker.extensions.now
import io.github.tcompose_date_picker.extensions.toEpochMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAdaptiveApi::class,
    ExperimentalCupertinoApi::class
)
@Composable
fun TKDateTimePicker(
    modifier: Modifier = Modifier,
    onDateTimeSelected: (LocalDateTime?) -> Unit,
    config: ConfigDateTimePicker = ConfigDateTimePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    colorsDate: DatePickerColors = DatePickerDefaults.colors(),
    colorsTime: TimePickerColors = TimePickerDefaults.colors(),
    textField: (@Composable (MutableInteractionSource) -> Unit)? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var tempDate by remember { mutableStateOf<LocalDate?>(null) }
    var tempTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val materialDatePickerState = rememberDatePickerState(
        yearRange = config.dateConfig.yearRange,
        initialSelectedDateMillis = config.dateConfig.initDate?.toEpochMillis()
    )
    val cupertinoDatePickerState = rememberCupertinoDatePickerState(
        yearRange = config.dateConfig.yearRange,
        initialSelectedDateMillis = config.dateConfig.initDate?.toEpochMillis() ?: LocalDate.now()
            .toEpochMillis()
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
                            withoutSeconds = config.timeConfig.showSeconds,
                            config.timeConfig.is24Hour
                        )
                }
            } ?: ""
        }
    }

    isDialogOpen(showDatePicker || showTimePicker)

    textField?.invoke(interactionSource) ?: OutlinedTextField(
        modifier = modifier,
        shape = shape,
        readOnly = true,
        value = formattedDateTime,
        colors = OutlinedTextFieldDefaults.colors(),
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
        interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect { interaction ->
                    if (interaction is PressInteraction.Release) {
                        showDatePicker = true
                    }
                }
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            modifier = dialogConfig.modifier,
            onDismissRequest = { showDatePicker = false; isDialogOpen(false) },
            confirmButton = {
                TextButton(onClick = {
                    if (config.dateConfig.useAdaptiveDialog) {
                        cupertinoDatePickerState.selectedDateMillis.let { millis ->
                            tempDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            showDatePicker = false
                            showTimePicker = true
                        }
                    } else {
                        materialDatePickerState.selectedDateMillis?.let { millis ->
                            tempDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            showDatePicker = false
                            showTimePicker = true
                        }
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
                if (config.dateConfig.useAdaptiveDialog)
                    AdaptiveDatePicker(
                        modifier = dialogConfig.dateDialogModifier,
//                    dateFormatter = config.dateConfig.dateFormatter
//                        ?: remember { DatePickerDefaults.dateFormatter() },
                        state = cupertinoDatePickerState,
//                    colors = colorsDate,
//                    showModeToggle = false
                    )
                else {
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
                        val selectedTime = Pair(timePickerState.hour, timePickerState.minute)
                        tempTime = selectedTime
                        val selectedDateTime = LocalDateTime(
                            date.year, date.month, date.dayOfMonth,
                            selectedTime.first, selectedTime.second
                        )
                        onDateTimeSelected(selectedDateTime)
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
