package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.databinding.ExoPlayerRootBinding
import com.google.accompanist.coil.rememberCoilPainter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView.SHOW_BUFFERING_ALWAYS

@Composable
fun EpisodePlayer(
    imageUrl: String?,
    title: String,
    mediaUrl: String
) {
    val context = LocalContext.current
    val mediaItem = MediaItem.fromUri(mediaUrl)
    val exoPlayer = SimpleExoPlayer.Builder(context).build().apply {
        setMediaItem(mediaItem)
        prepare()
        playWhenReady = true
    }
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        val (imageRef, titleRef, playerRef) = createRefs()

        AndroidViewBinding(
            factory = ExoPlayerRootBinding::inflate,
            modifier = Modifier
                .fillMaxWidth()
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
                    top.linkTo(parent.top, 12.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(220.dp)
                .constrainAs(titleRef) {
                    top.linkTo(parent.top, 12.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(imageRef.end, margin = 18.dp)
                    end.linkTo(parent.end, 40.dp)
                }
        )
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