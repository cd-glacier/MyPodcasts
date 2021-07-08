package cdglacier.mypodcasts.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.ui.component.LoadingEpisodeList

@Composable
fun HomeScreen() {
    LoadingEpisodeList()
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}