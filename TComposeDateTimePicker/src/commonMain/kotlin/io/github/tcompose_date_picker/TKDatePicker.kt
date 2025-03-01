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
import androidx.compose.material3.rememberDatePickerState
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
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDialog
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.dialogs.date_picker.AdaptiveDatePickerDialog
import io.github.tcompose_date_picker.extensions.formatLocalDate
import io.github.tcompose_date_picker.extensions.toEpochMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TKDatePicker(
    modifier: Modifier = Modifier,
    useAdaptive: Boolean = false,
    config: ConfigDatePicker = ConfigDatePicker(),
    dialogConfig: ConfigDialog = ConfigDialog(),
    colors: DatePickerColors = DatePickerDefaults.colors(),
    onDateSelected: (LocalDate?) -> Unit,
    inputFieldColors: TextFieldColors? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    isDialogOpen: (Boolean) -> Unit,
    textFieldType: TextFieldType = TextFieldType.Outlined, // اختيار النوع
    onDismiss: () -> Unit = {},
    enable: Boolean = true,


    ) {
    var showDatePicker by remember { mutableStateOf(false) }


    val materialDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = config.initDate?.toEpochMillis(),
        yearRange = config.yearRange
    )
    val adaptiveDatePickerState = rememberAdaptiveDatePickerState(
        initialSelectedDateMillis = config.initDate?.toEpochMillis(),
        yearRange = config.yearRange
    )

    var tempDate by remember { mutableStateOf(config.initDate) }
    val formattedDate by remember { derivedStateOf { tempDate?.formatLocalDate() ?: "" } }



    isDialogOpen(showDatePicker)

    val resolvedColors = when (textFieldType) {

        TextFieldType.Outlined -> inputFieldColors ?: OutlinedTextFieldDefaults.colors()
        TextFieldType.Filled -> inputFieldColors ?: TextFieldDefaults.colors()

        else -> null
    }
    val inputModifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedDate) {
        awaitEachGesture {
            awaitFirstDown(pass = PointerEventPass.Initial)
            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
            if (upEvent != null) {
                showDatePicker = true
            }
        }
    }
    // ✅ **استخدام `textField` الممرر أو `OutlinedTextField` كافتراضي**
    when (textFieldType) {
        is TextFieldType.Custom -> textFieldType.textField(inputModifier)
        TextFieldType.Outlined -> OutlinedTextField(
            modifier = inputModifier,
            shape = shape,
            readOnly = true,
            value = formattedDate,
            label = config.label,
            textStyle = config.style,
            enabled = enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            placeholder = config.placeholder,
            onValueChange = {},
            colors = resolvedColors!!,
        )

        TextFieldType.Filled -> TextField(
            modifier = modifier.width(IntrinsicSize.Max).pointerInput(formattedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showDatePicker = true
                    }
                }
            },
            shape = shape,
            readOnly = true,
            value = formattedDate,
            label = config.label,
            textStyle = config.style,
            enabled = enable,
            supportingText = config.supportingText,
            leadingIcon = config.leadingIcon,
            trailingIcon = config.trailingIcon,
            prefix = config.prefix,
            suffix = config.suffix,
            placeholder = config.placeholder,
            onValueChange = {},
            colors = resolvedColors!!,
        )
    }


    if (showDatePicker) {
        if (!useAdaptive) {
            io.github.tcompose_date_picker.dialogs.date_picker.DatePickerDialog(
                onDismiss = {
                    showDatePicker = false
                    isDialogOpen(false)
                    onDismiss()
                },
                dialogConfig = dialogConfig,
                datePickerState = materialDatePickerState,
                onDateSelected = {
                    materialDatePickerState.selectedDateMillis?.let { millis ->
                        tempDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        showDatePicker = false
                        val selectedDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        onDateSelected(selectedDate)
                    }
                },
                config = config,
                colors = colors,
            )
        } else {
            AdaptiveDatePickerDialog(
                onDismiss = {
                    showDatePicker = false
                    isDialogOpen(false)
                    onDismiss()
                },
                dialogConfig = dialogConfig,
                datePickerState = adaptiveDatePickerState,
                onDateSelected = {
                    adaptiveDatePickerState.selectedDateMillis?.let { millis ->
                        tempDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        showDatePicker = false
                        val selectedDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                        onDateSelected(selectedDate)
                    }
                },
                config = config,
                colors = colors,
            )
        }
    }
}
