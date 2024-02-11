package io.tbib.tcomposedatepicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.Locale
class ConfigDatePicker(
    val dateFormatPattern: String = "dd/MM/yyyy",
    val initDate: LocalDate = LocalDate.now(),
    val displayInitDate: LocalDate? = null,
    val yearRange: IntRange = IntRange(1900, 2100),
    val locale: Locale = Locale.getDefault(),
    val title: String = "Select a date",
    var allowedDateValidator: (LocalDate) -> Boolean = { true },
    val label: @Composable() (() -> Unit)? = null,
    val placeholder: @Composable() (() -> Unit)? = null,
    val style: TextStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
         val   enable: Boolean = true,
    val supportingText: @Composable() (() -> Unit)? = null,
    val leadingIcon: @Composable() (() -> Unit)? = null,
    val trailingIcon: @Composable() (() -> Unit)? = null,
    val prefix: @Composable() (() -> Unit)? = null,
    val suffix: @Composable() (() -> Unit)? = null,

) {
    companion object {
        fun activeDateInPastOnly(date: LocalDate): Boolean =
            LocalDate.now().isAfter(date)

        fun activeDateInFutureOnly(date: LocalDate, activeCurrentDay:Boolean = true): Boolean =
            LocalDate.now().isBefore(date.minusDays(if(activeCurrentDay) -1 else 0))

    }

}