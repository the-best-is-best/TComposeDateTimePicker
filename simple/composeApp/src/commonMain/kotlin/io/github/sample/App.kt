package io.github.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sample.theme.AppTheme
import io.github.tcompose_date_picker.TKDatePicker
import io.github.tcompose_date_picker.TKDateTimePicker
import io.github.tcompose_date_picker.TKTimePicker
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.extensions.toIsoStringWithOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TKDateTimePicker(

            textFieldType = TextFieldType.Filled,
            config = ConfigDateTimePicker(
                label = {
                    Text("Select date and time")
                }

            ),
            isDialogOpen = {

            },
            onDateTimeSelected = {
                println("date time selected is ${it?.toIsoStringWithOffset()}")
            }
        )

        TKDatePicker(
            config = ConfigDatePicker(
                label = {
                    Text("Select Date")
                }

            ),
            isDialogOpen = {

            },
            onDateSelected = {
                println("date time selected is $it")
            }
        )

        TKTimePicker(
            config = ConfigTimePicker(
                label = {
                    Text("Time")
                }
            ),
            isDialogOpen = {

            },
            onTimeSelected = {
                println("date time selected is $it")
            }
        )

    }
}

