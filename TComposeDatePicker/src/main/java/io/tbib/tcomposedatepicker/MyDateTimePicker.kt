package io.tbib.tcomposedatepicker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.tbib.tcomposedatepicker.configs.ConfigButtonDialog
import io.tbib.tcomposedatepicker.configs.ConfigDateTimePicker
import io.tbib.tcomposedatepicker.states.DateTimePickerState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MyDateTimePicker(
    modifier: Modifier,
    onDateTimeSelected: (LocalDateTime?) -> Unit,
    config: ConfigDateTimePicker,
    state: DateTimePickerState,
    configButtonDialog: ConfigButtonDialog,
    colorsDate: DatePickerColors,
    colorsTime: TimePickerColors,
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape

){


    val formattedDate by remember{
            derivedStateOf {
                DateTimeFormatter.ofPattern(config.dateConfig.dateFormatPattern + " " + config.timeConfig.timeFormatPattern).format(state. pickerDateTime?: LocalDateTime.now())

        }

    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                configButtonDialog.buttonOk,
                textStyle = configButtonDialog.textStyle,
                res = configButtonDialog.res

            ) {
                onDateTimeSelected(state.pickerDateTime)
                timeDialogState.show()
                dateDialogState.hide()
            }
            negativeButton(
                configButtonDialog.buttonCancel,
                textStyle = configButtonDialog.textStyle,
                res = configButtonDialog.res
            ) {
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
          state.  pickerDateTime = LocalDateTime.now()
            state.   pickerDateTime = LocalDateTime.of(it, state. pickerDateTime!!.toLocalTime())
        }
    }


    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok") {
                onDateTimeSelected(state. pickerDateTime)
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
            state. pickerDateTime = LocalDateTime.of(state. pickerDateTime!!.toLocalDate(), it)
        }
    }


    OutlinedTextField(
        modifier = modifier,
        shape = shape,
        readOnly = true,
        value = if(state. pickerDateTime == null) "" else formattedDate,
        colors = inputFieldColors,
        textStyle = config.style,
        enabled = config.enable,
        supportingText =config. supportingText,
        leadingIcon =config. leadingIcon,
        trailingIcon =config. trailingIcon,
        prefix = config.prefix,
        suffix = config.suffix,
        label = config.label,
        placeholder = config.placeholder,
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