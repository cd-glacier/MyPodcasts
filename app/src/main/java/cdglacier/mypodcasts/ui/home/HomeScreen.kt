package cdglacier.mypodcasts.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.EpisodeList
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun HomeScreen(
    latestEpisodes: List<Episode>?,
    episodeItemOnClick: (Episode) -> Unit,
    playButtonOnClick: (Episode) -> Unit,
    titleOnClick: (Episode.Channel) -> Unit
) {
    EpisodeList(
        episodes = latestEpisodes,
        itemOnClick = episodeItemOnClick,
        playButtonOnClick = playButtonOnClick,
        titleOnClick = titleOnClick
    )
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    MyPodcastsTheme {
        HomeScreen(
            latestEpisodes = null,
            episodeItemOnClick = {},
            playButtonOnClick = {},
            titleOnClick = {}
        )
    }
}