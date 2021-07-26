package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.databinding.ExoPlayerRootBinding
import cdglacier.mypodcasts.ui.modifier.swipeDownToDismiss
import com.google.accompanist.coil.rememberCoilPainter
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun EpisodePlayer(
    imageUrl: String?,
    title: String,
    exoPlayer: ExoPlayer,
    onDismissed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(12.dp, 4.dp)
            .fillMaxWidth()
            .swipeDownToDismiss(onDismissed)
    ) {
        ConstraintLayout {
            val (handleRef, imageRef, titleRef, playerRef) = createRefs()

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .constrainAs(handleRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(playerRef.top, 4.dp)
                    },
            )

            AndroidViewBinding(
                factory = ExoPlayerRootBinding::inflate,
                modifier = Modifier
                    .height(100.dp)
                    .constrainAs(playerRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                this.player.apply {
                    player = exoPlayer
                }
            }

            Image(
                painter = rememberCoilPainter(request = imageUrl, fadeIn = true),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top, 24.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 4.dp)
                    }
            )

            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(220.dp)
                    .constrainAs(titleRef) {
                        top.linkTo(parent.top, 24.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(imageRef.end, margin = 18.dp)
                        end.linkTo(parent.end, 40.dp)
                    },
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/*
@Preview
@Composable
fun EpisodePlayerPreview() {
    EpisodePlayer(
        imageUrl = null,
        title = "playing episode title!!!!!!!!!!!!!",
        mediaUrl = ""
    )
}
 */