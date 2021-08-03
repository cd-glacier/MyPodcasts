package cdglacier.mypodcasts.ui.setting

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
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun SubscribedChannelSetting() {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        Column {
            Text(
                text = "Subscribed Channels",
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
        }
    }
}

@Preview
@Composable
fun SubscribedChannelPreview() {
    MyPodcastsTheme {
        SubscribedChannelSetting()
    }
}