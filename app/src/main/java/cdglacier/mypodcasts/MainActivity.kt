package cdglacier.mypodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cdglacier.mypodcasts.data.channel.impl.FakeChannelRepositoryImpl
import cdglacier.mypodcasts.data.episode.FakeEpisodeRepositoryImpl
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.component.EpisodePlayer
import cdglacier.mypodcasts.ui.component.HeaderTabRow
import cdglacier.mypodcasts.ui.home.HomeScreen
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        val channelRepository = FakeChannelRepositoryImpl()
        val episodeRepository = FakeEpisodeRepositoryImpl()

        val factory = MainViewModel.Factory(channelRepository, episodeRepository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.refetchLatestEpisodes()

        setContent {
            MyPodcastsTheme {
                MyPodcastsApp(viewModel)
            }
        }
    }
}

@Composable
private fun MyPodcastsApp(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = MyPodcastsScreen.fromRoute(
        backstackEntry.value?.destination?.route
    )

    val latestEpisodes: List<Episode>? by viewModel.latestEpisodes.observeAsState(null)

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                HeaderTabRow(
                    allScreens = MyPodcastsScreen.values().toList(),
                    onTabSelected = { screen -> navController.navigate(screen.name) },
                    currentScreen = currentScreen
                )
            },
            bottomBar = {
                viewModel.playingEpisode?.let {
                    EpisodePlayer(
                        imageUrl = it.channel.imageUrl,
                        title = it.title,
                        mediaUrl = it.mediaUrl
                    )
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MyPodcastsScreen.Home.name
            ) {
                composable(MyPodcastsScreen.Home.name) {
                    HomeScreen(
                        latestEpisodes = latestEpisodes,
                        playButtonOnClick = { viewModel.updatePlayingEpisode(it) }
                    )
                }

                composable(MyPodcastsScreen.Channel.name) {
                    Text(MyPodcastsScreen.Channel.name)
                }

                composable(MyPodcastsScreen.Setting.name) {
                    Text(MyPodcastsScreen.Setting.name)
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MyPodcastsAppPreview() {

    MyPodcastsTheme {
        MyPodcastsApp()
    }
}
 */