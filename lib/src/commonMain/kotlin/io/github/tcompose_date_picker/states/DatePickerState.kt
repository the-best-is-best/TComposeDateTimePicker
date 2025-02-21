package io.github.tcompose_date_picker.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDate

@Composable
fun rememberDatePickerStates( initDate: LocalDate?= null ): DatePickerState {
    return rememberSaveable(saver = DatePickerState.Saver()) {
        DatePickerState(initDate)
    }
}
class DatePickerState internal constructor(
    initDate: LocalDate?= null
) {
    var pickerDate by  mutableStateOf(initDate)
    private var initDateSaver: LocalDate? = initDate


    fun reset() {
        pickerDate = initDateSaver
    }

    companion object {
        fun Saver(): Saver<DatePickerState, *> =
            listSaver(
                save = {
                    listOf(
                        it.pickerDate,
                        it.initDateSaver
                    )
                },
                restore = {
                    val data = DatePickerState(null)
                    data.pickerDate = it[0]
                    data.initDateSaver = it[1]
                    data
                }
            )

    }

}
