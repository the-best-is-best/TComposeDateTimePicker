package io.github.tcompose_date_picker.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class TextFieldType {
    object Outlined : TextFieldType()
    object Filled : TextFieldType()
    data class Custom(val textField: @Composable (Modifier) -> Unit) : TextFieldType()
}
