package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
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

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useArtwork = false
                controllerShowTimeoutMs = 0
                controllerHideOnTouch = false
                controllerAutoShow = true
                setShowBuffering(SHOW_BUFFERING_ALWAYS)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}

@Preview
@Composable
fun EpisodePlayerPreview() {
    EpisodePlayer(
        imageUrl = null,
        title = "playing episode title!!!!!!!!!!!!!",
        mediaUrl = ""
    )
}