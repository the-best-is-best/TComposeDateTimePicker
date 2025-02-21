package io.github.tcompose_date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.extensions.formatLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Composable
@ExperimentalMaterial3Api
fun MyTimePicker(
    modifier: Modifier,
    config: ConfigTimePicker = ConfigTimePicker(),
    onTimeSelected:  (LocalTime?) -> Unit,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape,


    ) {


    val formattedDate by remember{

            derivedStateOf {
                config.initTime
        }
    }

     var showTime by remember { mutableStateOf(false) }

    val timeState = rememberTimePickerState()

    LaunchedEffect(timeState){
        onTimeSelected(LocalTime(timeState.hour,timeState.minute))
    }
    OutlinedTextField(
        shape = shape,
        readOnly = true,
        modifier = modifier.width(IntrinsicSize.Max),
        value =  formattedDate.formatLocalDateTime(use24HourFormat = config.is24Hour),
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
                            showTime = true                        }
                    }
                }
            }
    )

    if(showTime){
        Popup(
            onDismissRequest = { showTime = false },
            alignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 64.dp)
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                TimePicker(
                    state = timeState,
                    colors = colors,


                )
            }
        }
    }
}
