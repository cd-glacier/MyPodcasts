package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun LoadMoreButton(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Load More",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Light),
        )

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview
@Composable
fun LoadMoreButtonPreview() {
    MyPodcastsTheme(darkTheme = true) {
        LoadMoreButton(
            onClick = {}
        )
    }
}