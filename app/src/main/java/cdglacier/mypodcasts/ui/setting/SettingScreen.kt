package cdglacier.mypodcasts.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun SettingScreen(
    subscribedChannels: List<Channel>?
) {
    subscribedChannels?.let {
        Column {
            SubscribedChannelSetting(
                channels = it
            )
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    MyPodcastsTheme() {
        SettingScreen(
            subscribedChannels = fakeChannels
        )
    }
}