package io.tbib.tcomposedatepicker.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

@Composable
fun rememberDateTimePickerStates( initDateTime: LocalDateTime? = null): DateTimePickerState {
    return rememberSaveable(saver = DateTimePickerState.Saver()  ) {
        DateTimePickerState(initDateTime)
    }
}
class DateTimePickerState internal constructor(
    initDateTime: LocalDateTime? = null
) {
    var pickerDateTime by  mutableStateOf(initDateTime)
    private var initDateTimeSaver: LocalDateTime? = initDateTime



    fun reset() {
        pickerDateTime = initDateTimeSaver
    }

    companion object {
        fun Saver(): Saver<DateTimePickerState, *> =
            listSaver(
                save = {
                    listOf(
                        it.pickerDateTime,
                        it.initDateTimeSaver
                    )
                },
                restore = {
                    val data = DateTimePickerState(null)
                    data.pickerDateTime = it[0]
                    data.initDateTimeSaver = it[1]
                    data
                }
            )

    }

}
