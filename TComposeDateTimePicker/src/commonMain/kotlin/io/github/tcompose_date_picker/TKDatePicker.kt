package io.github.tcompose_date_picker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
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
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveDatePicker
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.rememberCupertinoDatePickerState
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.extensions.formatLocalDate
import io.github.tcompose_date_picker.extensions.now
import io.github.tcompose_date_picker.extensions.toEpochMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAdaptiveApi::class,
    ExperimentalCupertinoApi::class
)
@Composable
fun TKDatePicker(
    modifier: Modifier = Modifier,
    config: ConfigDatePicker = ConfigDatePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    onDateSelected: (LocalDate?) -> Unit,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    inputFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
    textField: (@Composable (Modifier) -> Unit)? = null, // يمكن تمرير حقل مخصص
) {
    var showDatePicker by remember { mutableStateOf(false) }


    val materialDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = config.initDate?.toEpochMillis(),
        yearRange = config.yearRange
    )
    val cupertinoDatePickerState = rememberCupertinoDatePickerState(
        initialSelectedDateMillis = config.initDate?.toEpochMillis() ?: LocalDate.now()
            .toEpochMillis(),
        yearRange = config.yearRange
    )

   var tempDate by remember { mutableStateOf(config.initDate) }
    val formattedDate by remember { derivedStateOf { tempDate?.formatLocalDate() ?: "" } }



    isDialogOpen(showDatePicker)


    // ✅ **استخدام `textField` الممرر أو `OutlinedTextField` كافتراضي**
    textField?.invoke(modifier.width(IntrinsicSize.Max).pointerInput(formattedDate) {
        awaitEachGesture {
            awaitFirstDown(pass = PointerEventPass.Initial)
            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
            if (upEvent != null) {
                showDatePicker = true
            }
        }}) ?: OutlinedTextField(
        modifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedDate) {
            awaitEachGesture {
                awaitFirstDown(pass = PointerEventPass.Initial)
                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                if (upEvent != null) {
                    showDatePicker = true
                }
            }},
        shape = shape,
        readOnly = true,
        value = formattedDate,
        label = config.label,
        textStyle = config.style,
        enabled = config.enable,
        supportingText = config.supportingText,
        leadingIcon = config.leadingIcon,
        trailingIcon = config.trailingIcon,
        prefix = config.prefix,
        suffix = config.suffix,
        placeholder = config.placeholder,
        onValueChange = {},
        colors = inputFieldColors,
    )

    if (showDatePicker) {
        DatePickerDialog(
            modifier = dialogConfig.modifier,
            onDismissRequest = {
                showDatePicker = false
                isDialogOpen(false)
            },

            confirmButton = {
                TextButton(onClick = {
                    if (config.useAdaptiveDialog) {
                        cupertinoDatePickerState.selectedDateMillis.let { millis ->
                            val selectedDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            tempDate= selectedDate
                            onDateSelected(selectedDate)
                            showDatePicker = false
                        }
                    } else {
                        materialDatePickerState.selectedDateMillis?.let { millis ->
                            tempDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            showDatePicker = false
                            val selectedDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            onDateSelected(selectedDate)
                        }
                    }
                }) {
                    Text(dialogConfig.buttonOk, style = dialogConfig.textOKStyle)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    isDialogOpen(false)
                }) {
                    Text(dialogConfig.buttonCancel, style = dialogConfig.textCancelStyle)
                }
            },
            content = {
                if (config.useAdaptiveDialog) {
                    AdaptiveDatePicker(
                        modifier = dialogConfig.dateDialogModifier,
                        state = cupertinoDatePickerState,

                        )

                } else {
                    DatePicker(
                        modifier = dialogConfig.dateDialogModifier,
                        title = dialogConfig.title,
                        headline = dialogConfig.headline,
                        dateFormatter = config.dateFormatter
                            ?: remember { DatePickerDefaults.dateFormatter() },
                        state = materialDatePickerState,
                        colors = colors,
                        showModeToggle = true
                    )
                }
            }

        )
    }
}
