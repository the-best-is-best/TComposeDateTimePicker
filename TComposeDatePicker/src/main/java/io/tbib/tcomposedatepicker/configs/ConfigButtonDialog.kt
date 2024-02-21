package io.tbib.tcomposedatepicker.configs

import androidx.annotation.StringRes
import androidx.compose.ui.text.TextStyle

data class ConfigButtonDialog(
    val buttonOk: String = "Ok",
    val buttonCancel: String = "Cancel",
    val  textCancel: String? = null,
    val  textStyle: TextStyle ,
    @StringRes val res: Int? = null,
)
