package io.tbib.tcomposedatepicker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MyDateTimePicker(
    onDateTimeSelected: (LocalDateTime) -> Unit,
    config:ConfigDateTimePicker, colorsDate: DatePickerColors, colorsTime: TimePickerColors){

    var pickerDateTime by rememberSaveable {
        mutableStateOf(LocalDateTime.now())
    }
    val formattedDate by remember{
        derivedStateOf {
            DateTimeFormatter.ofPattern(config.dateFormatPattern).format(pickerDateTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok") {
                onDateTimeSelected(pickerDateTime)
                timeDialogState.show()
                dateDialogState.hide()
            }
            negativeButton("Cancel") {
                dateDialogState.hide()
            }
        }
    ) {
        datepicker(
            initialDate = config.dateConfig.initDate,
            yearRange = config.dateConfig.yearRange,
            locale = config.dateConfig.locale,
            title = config.dateConfig.title,
            allowedDateValidator = config.dateConfig.allowedDateValidator,
            colors = colorsDate

        ) {
            pickerDateTime = LocalDateTime.of(it, pickerDateTime.toLocalTime())
        }
    }


    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok") {
                onDateTimeSelected(pickerDateTime)
                timeDialogState.hide()
            }
            negativeButton("Cancel") {
                timeDialogState.hide()
            }
        }
    ) {
        timepicker(
            initialTime = config.timeConfig.initTime,
            is24HourClock = config.timeConfig.is24Hour,
            title = config.timeConfig.title,
            colors = colorsTime
        ) {
            pickerDateTime = LocalDateTime.of(pickerDateTime.toLocalDate(), it)
        }
    }


    OutlinedTextField(
        readOnly = true,
        value = formattedDate,
        onValueChange ={},
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            dateDialogState.show()
                        }
                    }
                }
            }
    )
}