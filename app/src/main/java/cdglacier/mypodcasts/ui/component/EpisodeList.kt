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
import cdglacier.mypodcasts.data.episode.fakeEpisodes
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun LoadingEpisodeList() {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        for (i in 0..2) {
            LoadingEpisodeItem()
            Divider(
                color = MaterialTheme.colors.background,
                thickness = 1.dp,
                startIndent = 8.dp
            )
        }
    }
}

@Composable
fun EpisodeList(
    episodes: List<Episode>?,
    playButtonOnClick: (Episode) -> Unit,
    titleOnClick: (Episode.Channel) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        Column {
            Text(
                text = "Latest Episodes",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
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
                episodes == null -> {
                    LoadingEpisodeList()
                }
                episodes.isEmpty() -> {
                    Text("empty")
                }
                else -> {
                    LazyColumn {
                        items(episodes) {
                            EpisodeItem(
                                episode = it,
                                playButtonOnClick = playButtonOnClick,
                                titleOnClick = titleOnClick
                            )
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
fun EpisodeListPreview() {
    MyPodcastsTheme(darkTheme = true) {
        EpisodeList(
            episodes = fakeEpisodes,
            playButtonOnClick = {},
            titleOnClick = {}
        )
    }
}
