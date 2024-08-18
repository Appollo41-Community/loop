import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.appollo41.loop.db.NoteDao
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Preview
@Composable
fun App(userDao: NoteDao) {
    MaterialTheme {


        KoinContext {
            NostrExample(dao = userDao)
        }

//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }

    }
}