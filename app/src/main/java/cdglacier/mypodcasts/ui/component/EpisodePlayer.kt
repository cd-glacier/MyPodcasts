package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun EpisodePlayer(
    imageUrl: String?,
    title: String
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        val (imageRef, titleRef, playButtonRef) = createRefs()

        Image(
            painter = rememberCoilPainter(request = imageUrl, fadeIn = true),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = title,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(imageRef.end, margin = 12.dp)
                }
        )

        IconButton(
            onClick = {},
            modifier = Modifier
                .constrainAs(playButtonRef) {
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
fun EpisodePlayerPreview() {
    EpisodePlayer(
        imageUrl = null,
        title = "playing episode title"
    )
}