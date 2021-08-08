package cdglacier.mypodcasts.ui.episode.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.data.episode.impl.fakeTalkingKotlinEpisodes
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.RoundedPlayButton
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun EpisodeDetail(
    episode: Episode,
    playButtonOnClick: (Episode) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        Column {
            Text(
                text = episode.title,
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

            EpisodeDetailBaseContent(episode = episode)

            ActionRow(
                playButtonOnClick = { playButtonOnClick(episode) }
            )

            Text(
                text = episode.description ?: "",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(12.dp)
            )

        }
    }
}

@Composable
fun EpisodeDetailBaseContent(episode: Episode) {
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        val (
            publishedAtRef,
            imageRef,
            channelNameRef,
            authorRef,
            webSiteUrlRef,
        ) = createRefs()

        episode.publishedAt?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(4.dp)
                    .constrainAs(publishedAtRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
        }

        Image(
            painter = rememberCoilPainter(request = episode.channel.imageUrl, fadeIn = true),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(imageRef) {
                    top.linkTo(publishedAtRef.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = episode.channel.name,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.constrainAs(channelNameRef) {
                top.linkTo(imageRef.top)
                start.linkTo(imageRef.end, 12.dp)
            }
        )

        Text(
            text = episode.channel.author ?: "",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(authorRef) {
                top.linkTo(channelNameRef.bottom)
                start.linkTo(imageRef.end, 12.dp)
            }
        )

        episode.episodeWebSiteUrl?.let {
            Text(
                text = episode.episodeWebSiteUrl,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(240.dp)
                    .constrainAs(webSiteUrlRef) {
                        top.linkTo(authorRef.bottom)
                        start.linkTo(imageRef.end, 12.dp)
                        bottom.linkTo(imageRef.bottom)
                    }
            )
        }
    }
}

@Composable
fun ActionRow(
    playButtonOnClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        RoundedPlayButton(
            onClick = playButtonOnClick
        )
    }
}

@Preview
@Composable
fun EpisodeDetailPreview() {
    MyPodcastsTheme {
        EpisodeDetail(
            episode = fakeTalkingKotlinEpisodes.first(),
            playButtonOnClick = {}
        )
    }
}
