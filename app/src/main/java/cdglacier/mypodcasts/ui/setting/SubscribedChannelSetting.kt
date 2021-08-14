package cdglacier.mypodcasts.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.data.channel.impl.fakeChannels
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.ui.component.ChannelItem
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun SubscribedChannelSetting(
    channels: List<Channel>,
    onAddChannel: (String) -> Unit,
    onRemoveChannel: (channel: Channel) -> Unit
) {
    val (text, setText) = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colors.surface)
            .padding(4.dp)
    ) {
        Column {
            Text(
                text = "Subscribed Channels",
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

            SubscribedChannelContent(
                channels = channels,
                text = text,
                onChange = setText,
                onAddChannel = {
                    onAddChannel(text)
                    setText("")
                },
                removeButtonOnClick = onRemoveChannel
            )
        }
    }
}

@Composable
fun SubscribedChannelContent(
    channels: List<Channel>,
    text: String,
    onChange: (String) -> Unit,
    onAddChannel: (String) -> Unit,
    removeButtonOnClick: (channel: Channel) -> Unit
) {
    Column {
        SubscribedChannelList(
            channels = channels,
            removeButtonOnClick = removeButtonOnClick
        )

        SubscribedChannelInputField(
            text = text,
            onChange = onChange,
            onAdd = onAddChannel,
        )
    }
}

@Composable
fun SubscribedChannelList(
    channels: List<Channel>,
    removeButtonOnClick: (channel: Channel) -> Unit
) {
    LazyColumn {
        items(channels) { it ->
            ChannelItem(
                channel = it,
                onClick = {},
                isRemovable = true,
                removeButtonOnClick = { removeButtonOnClick(it) },
            )

            Divider(
                color = MaterialTheme.colors.background,
                thickness = 1.dp,
                startIndent = 8.dp
            )
        }
    }
}

@Composable
fun SubscribedChannelInputField(
    text: String,
    onChange: (String) -> Unit,
    onAdd: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
    ) {
        val (inputRef, saveButtonRef) = createRefs()

        TextField(
            value = text,
            onValueChange = onChange,
            label = { Text(text = "Input RSS URL") },
            modifier = Modifier
                .width(270.dp)
                .constrainAs(inputRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Button(
            onClick = { onAdd(text) },
            modifier = Modifier
                .constrainAs(saveButtonRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = "ADD")
        }
    }
}

@Preview
@Composable
fun SubscribedChannelPreview() {
    MyPodcastsTheme {
        SubscribedChannelSetting(
            channels = fakeChannels,
            onAddChannel = {},
            onRemoveChannel = {}
        )
    }
}