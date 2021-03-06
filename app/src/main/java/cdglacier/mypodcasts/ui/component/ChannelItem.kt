package cdglacier.mypodcasts.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.model.Channel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ChannelItem(
    channel: Channel,
    onClick: (Channel) -> Unit,
    isRemovable: Boolean = false,
    removeButtonOnClick: (Channel) -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable { onClick(channel) }
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        val (imageRef, nameAndAuthorRef, removeButtonRef) = createRefs()

        Image(
            painter = rememberCoilPainter(request = channel.imageUrl, fadeIn = true),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(nameAndAuthorRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(imageRef.end, 12.dp)
                }
        ) {
            Text(
                text = channel.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = if (isRemovable) {
                    Modifier.width(200.dp)
                } else {
                    Modifier.width(250.dp)
                }
            )

            channel.author?.let {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = it,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (isRemovable) {
            IconButton(
                onClick = { removeButtonOnClick(channel) },
                modifier = Modifier
                    .constrainAs(removeButtonRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "remove button",
                    modifier = Modifier.size(100.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun ChannelItemPreview() {
    ChannelItem(
        Channel(
            id = 1,
            domain = "rebuild.fm",
            name = "Rebuild",
            author = "Tatsuhiko Miyagawa",
            imageUrl = "https://cdn.rebuild.fm/images/icon240.png",
            description = "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? #rebuildfm",
            newFeedsUrl = "https://feeds.soundcloud.com/users/soundcloud:users:280353173/sounds.rss",
            webSiteUrl = "https://rebuild.fm",
        ),
        onClick = {}
    )
}