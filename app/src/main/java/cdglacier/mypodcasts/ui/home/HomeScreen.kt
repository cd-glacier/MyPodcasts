package cdglacier.mypodcasts.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.LoadingEpisodeList

@Composable
fun HomeScreen(latestEpisodes: List<Episode>?) {
    when {
        latestEpisodes == null -> {
            LoadingEpisodeList()
        }
        latestEpisodes.isEmpty() -> {
            Text("empty")
        }
        else -> {
            LazyColumn {
                items(latestEpisodes) {
                    Text(it.title)
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(listOf())
}