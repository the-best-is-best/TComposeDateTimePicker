package io.tbib.tcomposedatepicker.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.LocalTime

@Composable
fun rememberTimePickerStates( initTime: LocalTime? = null): TimePickerState {
    return rememberSaveable(saver = TimePickerState.Saver()  ) {
        TimePickerState(initTime)
    }
}
class TimePickerState internal constructor(
    initTime: LocalTime? = null
) {
    var pickerTime by  mutableStateOf(initTime)
    private var initTimeSaver = initTime


    fun reset() {
        pickerTime = initTimeSaver
    }

    companion object {
        fun Saver(): Saver<TimePickerState, *> =
            listSaver(
                save = {
                    listOf(
                        it.pickerTime,
                        it.initTimeSaver
                    )
                },
                restore = {
                    val data = TimePickerState()
                    data.pickerTime = it[0]
                    data.initTimeSaver = it[1]
                    data
                }
            )

    }

}
