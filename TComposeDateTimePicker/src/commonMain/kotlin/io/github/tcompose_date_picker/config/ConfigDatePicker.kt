package io.github.tcompose_date_picker.config

import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate

class ConfigDatePicker @OptIn(ExperimentalMaterial3Api::class) constructor(
    val dateFormatter: DatePickerFormatter? = null,
    val initDate: LocalDate? = null,
    val yearRange: IntRange = IntRange(1900, 2100),
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,

    val label: @Composable (() -> Unit)? = null,
    val placeholder: @Composable (() -> Unit)? = null,
    val style: TextStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
    val supportingText: @Composable (() -> Unit)? = null,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val prefix: @Composable (() -> Unit)? = null,
    val suffix: @Composable (() -> Unit)? = null,


    )