package cdglacier.mypodcasts.ui.channel.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.EpisodeList
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun ChannelDetailScreen(
    channel: Channel?,
    episodes: List<Episode>?,
    playButtonOnClick: (Episode) -> Unit,
) {
    Column {
        ChannelDetail(channel = channel)

        EpisodeList(
            episodes = episodes,
            playButtonOnClick = playButtonOnClick,
            titleOnClick = {}
        )
    }
}

@Preview
@Composable
fun ChannelDetailScreenPreview() {
    MyPodcastsTheme {
        ChannelDetailScreen(
            channel = fakeChannels.first(),
            episodes = listOf(),
            playButtonOnClick = {}
        )
    }
}