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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tcomposedatetimepicker.ui.theme.TComposeDateTimePickerTheme
import io.tbib.tcomposedatepicker.TDatePicker
import io.tbib.tcomposedatepicker.configs.ConfigButtonDialog
import io.tbib.tcomposedatepicker.configs.ConfigDatePicker
import io.tbib.tcomposedatepicker.configs.ConfigDatePicker.Companion.activeDateInFutureOnly
import io.tbib.tcomposedatepicker.configs.ConfigDateTimePicker
import io.tbib.tcomposedatepicker.configs.ConfigTimePicker
import io.tbib.tcomposedatepicker.states.rememberDatePickerStates
import io.tbib.tcomposedatepicker.states.rememberDateTimePickerStates
import io.tbib.tcomposedatepicker.states.rememberTimePickerStates
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
    val stateDate = rememberDatePickerStates(
        initDate = dateTime.toLocalDate(),

    )

    val stateTime = rememberTimePickerStates(
        initTime  = dateTime.toLocalTime()
    )

    val stateDateTime = rememberDateTimePickerStates(
        initDateTime = dateTime
    )



    Column {
            TDatePicker.ShowDatePicker(
                state = stateDate,

                config = ConfigDatePicker(
                    placeholder = {
                        Text("Select Date")
                    },
                    label = {
                        Text("Date")
                    },

                    allowedDateValidator = {activeDateInFutureOnly(it, false)},
                    yearRange = IntRange(LocalDate.now().year, LocalDate.now().year+100),
                ),
                onDateSelected = {
                    dateTime = LocalDateTime.of(it, dateTime.toLocalTime())
                  Log.d("MainActivity", "Selected date: $dateTime")
                }
            )
        Spacer(modifier =Modifier.height(16.dp))
        TDatePicker.ShowTimePicker(
            configButtonDialog = ConfigButtonDialog(
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    letterSpacing = 1.25.sp
                ),
                buttonCancel = "الغاء",
                buttonOk = "تأكيد"
            ),
            config = ConfigTimePicker(
                placeholder = {
                    Text("Select Time", style = TextStyle(fontSize = 20.sp, color = Color.Black))
                }
            ),
            state = stateTime,
           onTimeSelected = {
                    dateTime = LocalDateTime.of(dateTime.toLocalDate(), it)
                    Log.d("MainActivity", "Selected time: $it")
                }

        )
        Spacer(modifier = Modifier.height(16.dp))
        TDatePicker.ShowDateTimePicker(
            config = ConfigDateTimePicker(
                placeholder = {
                    Text("Select Date and Time", style = TextStyle(fontSize = 20.sp, color = Color.Black))
                }
            ),
            state = stateDateTime,
            onDateTimeSelected ={
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