import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.appollo41.loop.db.getDatabaseBuilder
import com.appollo41.loop.di.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Loop",
    ) {
        val dao = remember {
            getDatabaseBuilder().getDao()
        }

        NostrExample(dao = dao)
    }
}