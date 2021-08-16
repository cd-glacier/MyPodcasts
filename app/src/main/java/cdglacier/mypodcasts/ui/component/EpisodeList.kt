package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cdglacier.mypodcasts.data.episode.impl.fakeRebuildEpisodes
import cdglacier.mypodcasts.data.episode.impl.fakeTalkingKotlinEpisodes
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
    limit: Int = 5,
    visibleImage: Boolean = true,
    itemOnClick: (Episode) -> Unit,
    playButtonOnClick: (Episode) -> Unit,
    titleOnClick: (Episode.Channel) -> Unit,
    loadMoreOnClick: () -> Unit
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
                    Column {
                        episodes.take(limit).forEach {
                            EpisodeItem(
                                episode = it,
                                visibleImage = visibleImage,
                                onClick = itemOnClick,
                                playButtonOnClick = playButtonOnClick,
                                titleOnClick = titleOnClick
                            )
                            Divider(
                                color = MaterialTheme.colors.background,
                                thickness = 1.dp,
                                startIndent = 8.dp
                            )
                        }

                        if (episodes.size > limit) {
                            LoadMoreButton(
                                onClick = loadMoreOnClick
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
            episodes = fakeRebuildEpisodes + fakeTalkingKotlinEpisodes,
            limit = 1,
            itemOnClick = {},
            playButtonOnClick = {},
            titleOnClick = {},
            loadMoreOnClick = {}
        )
    }
}
