package io.tbib.tcomposedatepicker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
@ExperimentalMaterial3Api
fun MyTimePicker(
    config: ConfigTimePicker = ConfigTimePicker(),
    onTimeSelected: (LocalTime) -> Unit,
     colors: TimePickerColors = TimePickerDefaults.colors()

) {

    var pickerTime by rememberSaveable {
        mutableStateOf(LocalTime.now())
    }
    val formattedDate by remember{
        derivedStateOf {
            DateTimeFormatter.ofPattern(config.dateFormatPattern).format(pickerTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()


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

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok") {
                onTimeSelected(pickerTime)
                dateDialogState.hide()
            }
            negativeButton("Cancel") {
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
            pickerTime = it
        }
    }
}
