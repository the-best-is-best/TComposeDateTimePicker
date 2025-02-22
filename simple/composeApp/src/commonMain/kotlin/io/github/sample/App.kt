package io.github.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sample.theme.AppTheme
import io.github.tcompose_date_picker.TKDatePicker
import io.github.tcompose_date_picker.TKDateTimePicker
import io.github.tcompose_date_picker.TKTimePicker
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDateTimePicker
import io.github.tcompose_date_picker.config.ConfigTimePicker
import io.github.tcompose_date_picker.config.TextFieldType
import io.github.tcompose_date_picker.extensions.formatLocalTime
import io.github.tcompose_date_picker.extensions.toIsoStringWithOffset
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    var time by remember { mutableStateOf<LocalTime?>(null) }
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
            textFieldType = TextFieldType.Custom { modifier ->
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Text(
                        text = "Time",

                        )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // داخل weight في Row
                            .border(1.dp, Color(0xff666666), RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Cyan)
                            .padding(horizontal = 10.dp, vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = time?.formatLocalTime() ?: "",

                            )
                    }
                }

            },
            config = ConfigTimePicker(
                label = {
                    Text("Time")
                }
            ),
            isDialogOpen = {

            },
            onTimeSelected = {
                time = it
                println("date time selected is $it")
            },
        )

    }
}

