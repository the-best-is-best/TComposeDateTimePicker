package io.github.tcompose_date_picker.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalTime

class ConfigTimePicker(
    val initTime: LocalTime? = null,
    val title: String = "Select a time",
    val is24Hour: Boolean = false,
    val label: @Composable() (() -> Unit)? = null,
    val placeholder: @Composable() (() -> Unit)? = null,
    val  style: TextStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
    val supportingText: @Composable() (() -> Unit)? = null,
    val leadingIcon: @Composable() (() -> Unit)? = null,
    val trailingIcon: @Composable() (() -> Unit)? = null,
    val prefix: @Composable() (() -> Unit)? = null,
    val suffix: @Composable() (() -> Unit)? = null,

    )