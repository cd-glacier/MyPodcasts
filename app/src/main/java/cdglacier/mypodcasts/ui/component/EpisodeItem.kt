package cdglacier.mypodcasts.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.model.Episode
import com.google.accompanist.coil.rememberCoilPainter

private val itemPadding = 12.dp
private val titleAndAuthorWidth = 232.dp

@Composable
fun LoadingEpisodeItem() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val loadingAnimationColor = Color.Gray.copy(alpha = alpha)

    ConstraintLayout(
        modifier = Modifier
            .padding(itemPadding)
            .fillMaxWidth()
    ) {
        val (publishedAt, image, titleAndAuthor, playButton) = createRefs()

        Box(
            Modifier
                .width(100.dp)
                .height(12.dp)
                .background(loadingAnimationColor)
                .constrainAs(publishedAt) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Box(
            Modifier
                .width(80.dp)
                .height(80.dp)
                .background(loadingAnimationColor)
                .constrainAs(image) {
                    top.linkTo(publishedAt.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(titleAndAuthor) {
                    start.linkTo(image.end, margin = 12.dp)
                    top.linkTo(image.top, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 4.dp)
                }
        ) {
            Box(
                Modifier
                    .width(200.dp)
                    .height(32.dp)
                    .background(loadingAnimationColor)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                Modifier
                    .width(180.dp)
                    .height(24.dp)
                    .background(loadingAnimationColor)
            )
        }

        Box(
            Modifier
                .height(64.dp)
                .width(64.dp)
                .clip(CircleShape)
                .background(loadingAnimationColor)
                .constrainAs(playButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview
@Composable
fun LoadingEpisodeItemPreview() {
    LoadingEpisodeItem()
}

@Composable
fun EpisodeItem(
    episode: Episode,
    playButtonOnClick: (Episode) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(itemPadding)
            .fillMaxWidth()
    ) {
        val (publishedAtRef, image, titleAndAuthor, playButton) = createRefs()

        Text(
            episode.publishedAt,
            modifier = Modifier
                .constrainAs(publishedAtRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Image(
            painter = rememberCoilPainter(request = episode.channel.imageUrl, fadeIn = true),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    top.linkTo(publishedAtRef.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(titleAndAuthor) {
                    start.linkTo(image.end, margin = 12.dp)
                    end.linkTo(playButton.start, margin = 12.dp)
                    top.linkTo(image.top, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 4.dp)
                }
        ) {
            Text(
                text = episode.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(titleAndAuthorWidth)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = episode.channel.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(titleAndAuthorWidth)
            )
        }

        IconButton(
            onClick = { playButtonOnClick(episode) },
            modifier = Modifier
                .constrainAs(playButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayCircle,
                contentDescription = "play button",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    EpisodeItem(
        episode = Episode(
            title = "Kotlin in Education (Talking Kotlin #101)",
            description = "In this episode, we’ll sit down with Ksenia Shneyveys, the Kotlin Manager for Education and University Relations at JetBrains, and talk to her about the current state and future of Kotlin in academia. Kseniya will tell us about the recent increase in institutions and educators teaching Kotlin, including adoption by Stanford, Cambridge, Imperial College London, University of Chicago, and many other prestigious institutions.",
            publishedAt = "Sat, 17 Jul 2021 13:45:00 +0000",
            mediaUrl = "https://feeds.soundcloud.com/stream/1088610637-user-38099918-kotlin-in-education-talking-kotlin-101.mp3",
            lengthSecond = 30439966,
            episodeWebSiteUrl = null,
            channel = Episode.Channel(
                name = "Talking Kotlin",
                imageUrl = "https://i1.sndcdn.com/avatars-000289370353-di6ese-original.jpg"
            )
        ),
        playButtonOnClick = {}
    )
}