package cdglacier.mypodcasts.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

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
            .padding(8.dp)
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

/*
@Composable
fun EpisodeItem(
    title: String,
    author: String,
    imageUrl: String,
    publishedAt: String, // TODO
    episodeLengthSecond: Long
) {
    Column {
        Text(text = "x days ago")
        Row {

        }
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    EpisodeItem(
        title = "MyPodcasts App is developing",
        author = "cd-glacier",
        imageUrl = "img",
        publishedAt = "xxxx",
        episodeLengthSecond = 12345
    )
}
 */