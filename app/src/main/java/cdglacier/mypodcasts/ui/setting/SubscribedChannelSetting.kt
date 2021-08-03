package cdglacier.mypodcasts.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun SubscribedChannelSetting() {
    // TODO: replace room
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
                text = text,
                onChange = setText
            )
        }
    }
}

@Composable
fun SubscribedChannelContent(
    text: String,
    onChange: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val (inputRef, saveButtonRef) = createRefs()

        TextField(
            value = text,
            onValueChange = onChange,
            modifier = Modifier.constrainAs(inputRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(saveButtonRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(inputRef.end)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = "SAVE")
        }
    }
}

@Preview
@Composable
fun SubscribedChannelPreview() {
    MyPodcastsTheme {
        SubscribedChannelSetting()
    }
}