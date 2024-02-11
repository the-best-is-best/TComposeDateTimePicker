package io.tbib.tcomposedatepicker

import androidx.compose.runtime.Composable
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
    val  label : @Composable ()->Unit = {},
    val    placeholder : @Composable ()->Unit = {},
) {
    companion object {
        fun activeDateInPastOnly(date: LocalDate): Boolean =
            LocalDate.now().isAfter(date)

        fun activeDateInFutureOnly(date: LocalDate, activeCurrentDay:Boolean = true): Boolean =
            LocalDate.now().isBefore(date.minusDays(if(activeCurrentDay) -1 else 0))

    }

}