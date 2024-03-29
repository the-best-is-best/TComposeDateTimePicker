package io.tbib.tcomposedatepicker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.tbib.tcomposedatepicker.configs.ConfigButtonDialog
import io.tbib.tcomposedatepicker.configs.ConfigTimePicker
import io.tbib.tcomposedatepicker.states.TimePickerState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
@ExperimentalMaterial3Api
fun MyTimePicker(
    modifier: Modifier,
    config: ConfigTimePicker = ConfigTimePicker(),
    configButtonDialog: ConfigButtonDialog,
    state : TimePickerState,
    onTimeSelected: (LocalTime?) -> Unit,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape,


    ) {


    val formattedDate by remember{

            derivedStateOf {
                DateTimeFormatter.ofPattern(config.timeFormatPattern).format(state.pickerTime)

        }
    }
    val dateDialogState = rememberMaterialDialogState()


    OutlinedTextField(
        shape = shape,
        readOnly = true,
        modifier = modifier.width(IntrinsicSize.Max),
        value = if(state.pickerTime == null) "" else formattedDate,
        label = config.label,
        placeholder = config.placeholder,
        textStyle = config.style,
        enabled = config.enable,
        supportingText =config. supportingText,
        leadingIcon =config. leadingIcon,
        trailingIcon =config. trailingIcon,
        prefix = config.prefix,
        suffix = config.suffix,
        colors = inputFieldColors,
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

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                configButtonDialog.buttonOk,
                textStyle = configButtonDialog.textStyle,
                res= configButtonDialog.res
            ) {
                onTimeSelected(state.pickerTime)
                dateDialogState.hide()
            }
            negativeButton(
                configButtonDialog.buttonCancel,
                textStyle = configButtonDialog.textStyle,
                res= configButtonDialog.res
            ) {
                dateDialogState.hide()
            }
        }
    ) {
        timepicker(
           initialTime = config.initTime,
            title = config.title,
            timeRange = config.timeRange,
            is24HourClock = config.is24Hour,
            colors = colors



        ) {
            state.pickerTime = it
        }
    }
}
