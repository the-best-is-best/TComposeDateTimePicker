package com.example.tcomposedatetimepicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tcomposedatetimepicker.ui.theme.TComposeDateTimePickerTheme
import io.tbib.tcomposedatepicker.ConfigDatePicker
import io.tbib.tcomposedatepicker.ConfigDatePicker.Companion.activeDateInPastOnly
import io.tbib.tcomposedatepicker.TDatePicker

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column {
            TDatePicker.ShowDatePicker(
                config = ConfigDatePicker(
                ).activeDateInPastOnly(),
                onDateSelected = {
                  Log.d("MainActivity", "Selected date: $it")
                }
            )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TComposeDateTimePickerTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis > System.currentTimeMillis()-60*60*24*1000// Disables future dates
    }

    // If you need to disable specific years too:
    override fun isSelectableYear(year: Int): Boolean {
        return year >= 2024
    }
}
// Implement your logic here based on desired year restrictions
