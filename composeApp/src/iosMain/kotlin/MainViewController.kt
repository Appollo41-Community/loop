import androidx.compose.ui.window.ComposeUIViewController
import com.appollo41.loop.di.KoinInitializer

fun MainViewController() = ComposeUIViewController (
    configure = {
        KoinInitializer().init()
    }
) { App() }