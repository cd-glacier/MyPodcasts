package cdglacier.mypodcasts.ui.channel.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ChannelDetail(
    channel: Channel?
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        channel?.let { chan ->
            Column {
                Text(
                    text = chan.name,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )

                Divider(
                    color = MaterialTheme.colors.background,
                    thickness = 2.dp,
                    startIndent = 8.dp
                )

                ChannelDetailContent(chan)

                Text(
                    text = chan.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ChannelDetailPreview() {
    MyPodcastsTheme {
        ChannelDetail(channel = fakeChannels.first())
    }
}

@Composable
fun ChannelDetailContent(channel: Channel) {
    ConstraintLayout(
        modifier = Modifier.padding(12.dp)
    ) {
        val (imageRef, nameRef, authorRef, webSiteUrlRef) = createRefs()

        Image(
            painter = rememberCoilPainter(request = channel.imageUrl, fadeIn = true),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = channel.name,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(imageRef.top)
                start.linkTo(imageRef.end, 12.dp)
            }
        )

        Text(
            text = channel.author ?: "",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(authorRef) {
                top.linkTo(nameRef.bottom)
                start.linkTo(imageRef.end, 12.dp)
            }
        )

        Text(
            text = channel.webSiteUrl,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.constrainAs(webSiteUrlRef) {
                top.linkTo(authorRef.bottom)
                start.linkTo(imageRef.end, 12.dp)
                bottom.linkTo(imageRef.bottom)
            }
        )
    }
}