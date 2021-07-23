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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cdglacier.mypodcasts.model.Episode

@Composable
fun LoadingEpisodeList() {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
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
}

@Preview
@Composable
fun LoadingEpisodeListPreview() {
    LoadingEpisodeList()
}

@Composable
fun EpisodeList(
    episodes: List<Episode>,
    playButtonOnClick: (Episode) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        LazyColumn {
            items(episodes) {
                EpisodeItem(
                    episode = it,
                    playButtonOnClick = playButtonOnClick
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
