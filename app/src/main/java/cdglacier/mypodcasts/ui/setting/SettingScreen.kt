package cdglacier.mypodcasts.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

@Composable
fun SettingScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        SubscribedChannelSetting()
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    MyPodcastsTheme() {
        SettingScreen()
    }
}