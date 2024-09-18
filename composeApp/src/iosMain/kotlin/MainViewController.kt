import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.appollo41.loop.db.getDatabaseBuilder
import com.appollo41.loop.di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) {
    val dao = remember {
        getDatabaseBuilder().getDao()
    }

    App(dao)
}