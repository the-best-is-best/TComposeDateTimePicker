package com.example.tcomposedatetimepicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tcomposedatetimepicker.ui.theme.TComposeDateTimePickerTheme
import io.tbib.tcomposedatepicker.ConfigDatePicker
import io.tbib.tcomposedatepicker.ConfigDatePicker.Companion.activeDateInFutureOnly
import io.tbib.tcomposedatepicker.TDatePicker
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TComposeDateTimePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var dateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    Column {
            TDatePicker.ShowDatePicker(
                config = ConfigDatePicker(
                    allowedDateValidator = {activeDateInFutureOnly(it, false)},
                    yearRange = IntRange(LocalDate.now().year, LocalDate.now().year+100),
                ),
                onDateSelected = {
                    dateTime = LocalDateTime.of(it, dateTime.toLocalTime())
                  Log.d("MainActivity", "Selected date: ${dateTime}")
                }
            )
        Spacer(modifier =Modifier.height(16.dp))
        TDatePicker.ShowTimePicker(
           onTimeSelected = {
                    dateTime = LocalDateTime.of(dateTime.toLocalDate(), it)
                    Log.d("MainActivity", "Selected time: $it")
                }

        )
        Spacer(modifier = Modifier.height(16.dp))
        TDatePicker.ShowDateTimePicker(onDateTimeSelected ={
            dateTime = it
            Log.d("MainActivity", "Selected date and time: $it")
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TComposeDateTimePickerTheme {
        Greeting("Android")
    }
}