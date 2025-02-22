package io.github.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.sample.theme.AppTheme
import io.github.tcompose_date_picker.TKDatePicker
import io.github.tcompose_date_picker.TKDateTimePicker
import io.github.tcompose_date_picker.TKTimePicker
import io.github.tcompose_date_picker.config.ConfigDatePicker
import io.github.tcompose_date_picker.config.ConfigDateTimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme(
    theme = Theme.Material3,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TKDateTimePicker(
            config = ConfigDateTimePicker(
                dateConfig = ConfigDatePicker(
                    useAdaptiveDialog = true
                )
            ),
            isDialogOpen = {

            },
            onDateTimeSelected = {
                println("date time selected is $it")
            }
        )

        TKDatePicker(
            isDialogOpen = {

            },
            onDateSelected = {
                println("date time selected is $it")
            }
        )

        TKTimePicker(
            isDialogOpen = {

            },
            onTimeSelected = {
                println("date time selected is $it")
            }
        )

    }
}