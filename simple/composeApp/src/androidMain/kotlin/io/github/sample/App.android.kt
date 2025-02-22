package io.github.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.sample.theme.AppTheme

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(
                theme = Theme.Material3,
            ) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
