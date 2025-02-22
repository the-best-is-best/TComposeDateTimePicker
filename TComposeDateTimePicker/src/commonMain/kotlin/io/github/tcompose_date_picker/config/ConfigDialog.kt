package io.github.tcompose_date_picker.config

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


data class ConfigDialog(
    val modifier: Modifier = Modifier,
    val dateDialogModifier: Modifier = Modifier.padding(top = 20.dp),
    val timeDialogModifier: Modifier = Modifier,
    val buttonOk: String = "Ok",
    val buttonCancel: String = "Cancel",
    val buttonNext: String = "Next",
    val textOKStyle: TextStyle = TextStyle.Default,
    val textCancelStyle: TextStyle = TextStyle.Default,
    val title: @Composable (() -> Unit)? = null,
    val headline: @Composable (() -> Unit)? = null,

    )
