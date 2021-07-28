package cdglacier.mypodcasts.ui.channel.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cdglacier.mypodcasts.model.Channel

@Composable
fun ChannelDetail(
    channel: Channel
) {
    Text(text = "Channel Detail: ${channel.name}")
}