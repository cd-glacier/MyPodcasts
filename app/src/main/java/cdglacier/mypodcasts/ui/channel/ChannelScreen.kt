package cdglacier.mypodcasts.ui.channel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.ui.component.ChannelList

@Composable
fun ChannelScreen(
    subscribedChannel: List<Channel>?
) {
    ChannelList(channels = subscribedChannel)
}

@Preview
@Composable
fun ChannelScreenPreview() {
    ChannelScreen(
        subscribedChannel = fakeChannels
    )
}