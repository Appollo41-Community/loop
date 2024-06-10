import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appollo41.loop.core.generateRandomString
import com.appollo41.loop.networking.NostrEvent
import com.appollo41.loop.networking.NostrIncomingMessage
import com.appollo41.loop.networking.SocketClient
import com.appollo41.loop.networking.filterBySubscriptionId
import com.appollo41.loop.security.hexToNpubHrp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun NostrExample() {
    val subscriptionId by remember { mutableStateOf(generateRandomString()) }
    val socketClient by remember { mutableStateOf(
        SocketClient(
            url = "wss://relay.primal.net",
        )
    ) }
    var connected by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    suspend fun connectToRelay() {
        socketClient.connect()
        delay(1_000)
        socketClient.sendREQ(
            subscriptionId = subscriptionId,
            data = buildJsonObject {
                put("kinds", buildJsonArray { add(1) })
                put("until", Clock.System.now().epochSeconds)
            }
        )
    }

    fun disconnectFromRelay() {
        socketClient.close()
    }

    val notes by produceState(initialValue = emptyList<NostrEvent>()) {
        socketClient.incomingMessages
            .filterBySubscriptionId(subscriptionId)
            .collect { incomingMessage ->
                if (incomingMessage is NostrIncomingMessage.EventMessage) {
                    incomingMessage.nostrEvent?.let { event ->
                        value = value.toMutableList().apply { add(0, event) }
                            .distinctBy { it.id }
                    }
                }
            }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (!connected) {
                                connectToRelay()
                                connected = true
                            } else {
                                disconnectFromRelay()
                                connected = false
                            }
                        }
                    }
                },
            ) {
                Text(text = if (!connected) "Connect to relay" else "Disconnect from relay")
            }

            val state = rememberLazyListState()

            LaunchedEffect(notes) {
                state.animateScrollToItem(index = 0)
            }

            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    count = notes.size,
                    key = { notes[it].id },
                ) {
                    Note(note = notes[it])
                }
            }
        }
    }
}

@Composable
fun Note(note: NostrEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(text = note.pubKey.hexToNpubHrp())
            Text(text = note.content)
        }

    }
}
