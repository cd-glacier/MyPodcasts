package cdglacier.mypodcasts.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.EpisodeList
import cdglacier.mypodcasts.ui.component.LoadingEpisodeList

@Composable
fun HomeScreen(
    latestEpisodes: List<Episode>?,
    playButtonOnClick: (Episode) -> Unit,
    titleOnClick: (Episode.Channel) -> Unit
) {
    when {
        latestEpisodes == null -> {
            LoadingEpisodeList()
        }
        latestEpisodes.isEmpty() -> {
            Text("empty", modifier = Modifier.padding(10.dp))
        }
        else -> {
            EpisodeList(
                episodes = latestEpisodes,
                playButtonOnClick = playButtonOnClick,
                titleOnClick = titleOnClick
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HomeScreen(
        latestEpisodes = null,
        playButtonOnClick = {},
        titleOnClick = {}
    )
}