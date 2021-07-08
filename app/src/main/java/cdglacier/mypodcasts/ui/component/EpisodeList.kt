package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingEpisodeList() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        for (i in 0..2) {
            LoadingEpisodeItem()
            Divider(color = MaterialTheme.colors.background, thickness = 1.dp)
        }
    }
}

@Preview
@Composable
fun LoadingEpisodeListPreview() {
    LoadingEpisodeList()
}