package cdglacier.mypodcasts.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.data.episode.fakeTalkingKotlinEpisodes
import cdglacier.mypodcasts.model.Episode
import com.google.accompanist.coil.rememberCoilPainter

private val itemPadding = 12.dp
private val titleAndAuthorWidth = 220.dp

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
    visibleImage: Boolean = true,
    onClick: (Episode) -> Unit,
    playButtonOnClick: (Episode) -> Unit,
    titleOnClick: (Episode.Channel) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(itemPadding)
            .fillMaxWidth()
            .clickable { onClick(episode) }
    ) {
        val (publishedAtRef, image, titleAndAuthor, playButton) = createRefs()

        episode.publishedAt?.let {
            Text(
                it,
                modifier = Modifier
                    .constrainAs(publishedAtRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                style = MaterialTheme.typography.caption
            )
        }

        if (visibleImage) {
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
        }

        Column(
            modifier = Modifier
                .constrainAs(titleAndAuthor) {
                    if (visibleImage) {
                        start.linkTo(image.end, margin = 12.dp)
                    } else {
                        start.linkTo(parent.start)
                    }
                    end.linkTo(playButton.start, margin = 12.dp)
                    top.linkTo(publishedAtRef.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 4.dp)
                }
        ) {
            Text(
                text = episode.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = if (visibleImage) {
                    Modifier.width(titleAndAuthorWidth)
                } else {
                    Modifier.width(300.dp)
                },
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = episode.channel.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .width(titleAndAuthorWidth)
                    .clickable { titleOnClick(episode.channel) }
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
        episode = fakeTalkingKotlinEpisodes.first(),
        onClick = {},
        playButtonOnClick = {},
        titleOnClick = {}
    )
}

@Preview
@Composable
fun EpisodeItemNonImagePreview() {
    EpisodeItem(
        episode = fakeTalkingKotlinEpisodes.first(),
        visibleImage = false,
        onClick = {},
        playButtonOnClick = {},
        titleOnClick = {}
    )
}
