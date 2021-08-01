package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun RoundedPlayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .height(36.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(40.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayCircle,
                    contentDescription = "play button",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Play Episode",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.width(14.dp))
        }
    }
}

@Preview
@Composable
fun RoundPlayButtonPreview() {
    MyPodcastsTheme {
        RoundedPlayButton(onClick = {})
    }
}