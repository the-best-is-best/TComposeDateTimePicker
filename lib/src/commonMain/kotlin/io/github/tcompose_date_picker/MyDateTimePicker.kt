package io.github.tcompose_date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.extensions.formatLocalDateTime
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDateTimePicker(
    modifier: Modifier = Modifier,
    onDateTimeSelected: (LocalDateTime?) -> Unit,
    config: ConfigDateTimePicker,
    colorsDate: DatePickerColors,
    colorsTime: TimePickerColors,
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }

    val formattedDateTime by remember {
        derivedStateOf {
            selectedDateTime?.formatLocalDateTime(
                withoutSeconds = config.timeConfig.showSeconds,
                config.timeConfig.is24Hour
            ) ?: ""
        }
    }

    // تحديث التاريخ عند اختيار المستخدم للتاريخ
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = Instant.fromEpochMilliseconds(millis)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date // فقط التاريخ بدون الوقت
            selectedDateTime = LocalDateTime(
                year = selectedDate.year,
                month = selectedDate.month,
                dayOfMonth = selectedDate.dayOfMonth,
                hour = timePickerState.hour,
                minute = timePickerState.minute
            )
            showDatePicker = false
            showTimePicker = true // فتح منتقي الوقت بعد تحديد التاريخ
        }
    }

    // تحديث الوقت عند اختيار المستخدم للوقت
    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        selectedDateTime?.let { selectedDate ->
            selectedDateTime = LocalDateTime(
                year = selectedDate.year,
                month = selectedDate.month,
                dayOfMonth = selectedDate.dayOfMonth,
                hour = timePickerState.hour,
                minute = timePickerState.minute
            )
            onDateTimeSelected(selectedDateTime)
        }
    }

    OutlinedTextField(
        modifier = modifier,
        shape = shape,
        readOnly = true,
        value = formattedDateTime,
        colors = inputFieldColors,
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
                    state = datePickerState,
                    colors = colorsDate,
                    title = { Text(config.dateConfig.title) },
                    showModeToggle = false
                )
            }
        }
    }

    if (showTimePicker) {
        Popup(
            onDismissRequest = { showTimePicker = false },
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
                    state = timePickerState,
                    colors = colorsTime
                )
            }
        }
    }
}
