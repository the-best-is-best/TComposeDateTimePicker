import androidx.compose.ui.window.ComposeUIViewController
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.sample.App
import io.github.sample.theme.AppTheme
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    AppTheme(
        theme = Theme.Material3

    ) {
        App()
    }
}