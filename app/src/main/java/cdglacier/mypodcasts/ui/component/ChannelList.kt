package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel

@Composable
fun ChannelList(
    channels: List<Channel>?
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        Column() {
            Text(
                text = "Subscribed Channels",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            Divider(
                color = MaterialTheme.colors.background,
                thickness = 2.dp,
                startIndent = 8.dp
            )

            when {
                channels == null -> {
                    Text("loading")
                }
                channels.isEmpty() -> {
                    Text("empty")
                }
                else -> {
                    LazyColumn {
                        items(items = channels) { item ->
                            ChannelItem(channel = item)

                            Divider(
                                color = MaterialTheme.colors.background,
                                thickness = 1.dp,
                                startIndent = 8.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChannelListPreview() {
    ChannelList(fakeChannels)
}