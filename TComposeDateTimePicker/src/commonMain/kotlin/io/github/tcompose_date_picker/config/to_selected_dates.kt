package io.github.tcompose_date_picker.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import io.github.tcompose_date_picker.extensions.toEpochMillisAtUtc
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
fun toSelectableDates(minDate: LocalDate?, maxDate: LocalDate?): SelectableDates {
    return object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val afterMin = minDate?.let { it.toEpochMillisAtUtc() <= utcTimeMillis } ?: true
            val beforeMax = maxDate?.let { it.toEpochMillisAtUtc() >= utcTimeMillis } ?: true
            return afterMin && beforeMax
        }
    }
}