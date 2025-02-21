package io.github.tcompose_date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
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
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.extensions.formatLocalDate
import io.github.tcompose_date_picker.extensions.now
import io.github.tcompose_date_picker.states.DatePickerState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyDatePicker(
    modifier: Modifier,
    config: ConfigDatePicker,
    onDateSelected:(LocalDate?)->Unit,
    colors : DatePickerColors,
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape

){

      val formattedDate by remember{

              derivedStateOf {
                  config.initDate
          }

      }
     var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = kotlinx.datetime.Instant.fromEpochMilliseconds(millis)
                .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
            onDateSelected(selectedDate)
        }
    }

      OutlinedTextField(
          modifier = modifier,
            shape = shape,
          readOnly = true,
          value = if(formattedDate == null)  "" else formattedDate!!.formatLocalDate(),
          label = config.label,
          textStyle = config.style,
          enabled = config.enable,
          supportingText =config. supportingText,
          leadingIcon =config. leadingIcon,
          trailingIcon =config. trailingIcon,
          prefix = config.prefix,
          suffix = config.suffix,
          placeholder = config.placeholder,
          onValueChange = {},
          colors = inputFieldColors,
          interactionSource = remember { MutableInteractionSource() }
              .also { interactionSource ->
                  LaunchedEffect(interactionSource) {
                      interactionSource.interactions.collect { interaction ->
                          if (interaction is PressInteraction.Release) {
                              showDatePicker = true
                          }
                      }
                  }
              }
      )
    if(showDatePicker){
        Popup(
            onDismissRequest = { showDatePicker = false },
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
                DatePicker(
                    dateFormatter = config.dateFormatter?: remember { androidx.compose.material3.DatePickerDefaults.dateFormatter() },
                    state = datePickerState,
                    colors = colors,
                    title = {
                       Text(config.title)
                    },
                    showModeToggle = false

                )
            }
        }
    }
}