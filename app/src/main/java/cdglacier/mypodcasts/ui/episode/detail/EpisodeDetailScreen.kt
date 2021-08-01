package cdglacier.mypodcasts.ui.episode.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cdglacier.mypodcasts.model.Episode

@Composable
fun EpisodeDetailScreen(
    episode: Episode?,
    playButtonOnClick: (Episode) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        episode?.let {
            EpisodeDetail(
                episode = it,
                playButtonOnClick = playButtonOnClick
            )
        }
    }
}