package io.tbib.tcomposedatepicker

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
@OptIn(ExperimentalMaterial3Api::class)
class ConfigDatePicker(
    var displayMode: DisplayMode = DisplayMode.Picker,
    var initialDisplayedMonthMillis: Long? = null,
    var initialSelectedDateMillis: Long? = null,
    var yearRange: IntRange = DatePickerDefaults.YearRange,
    var selectableDates: SelectableDates = DatePickerDefaults.AllDates
) {
    companion object {
        fun ConfigDatePicker.activeDateInFutureOnly(): ConfigDatePicker {
            selectableDates = FutureSelectableDates
            return this
        }

        fun ConfigDatePicker.activeDateInPastOnly(): ConfigDatePicker {
            selectableDates = PastSelectableDates
            return this
        }
    }

    internal object PastSelectableDates : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis  < System.currentTimeMillis()-60*60*24*1000// Disables future dates
        }

        // If you need to disable specific years too:
        override fun isSelectableYear(year: Int): Boolean {
            return year <= 2024
        }
    }

    internal object FutureSelectableDates : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis > System.currentTimeMillis()-60*60*24*1000// Disables past dates
        }

        // If you need to disable specific years too:
        override fun isSelectableYear(year: Int): Boolean {
            return year >= 2024
        }
    }

}