package cdglacier.mypodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

class MainActivity : ComponentActivity() {
    private val exoPlayer: ExoPlayer by lazy {
        SimpleExoPlayer.Builder(this).build()
    }

    private val viewModel: MainViewModel by lazy {
        val channelRepository = FakeChannelRepositoryImpl()
        val episodeRepository = FakeEpisodeRepositoryImpl()

        val factory = MainViewModel.Factory(exoPlayer, channelRepository, episodeRepository)
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

    override fun onDestroy() {
        super.onDestroy()

        viewModel.invalidate()
    }
}

@OptIn(ExperimentalAnimationApi::class)
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
                AnimatedVisibility(
                    visible = viewModel.playingEpisode != null,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
                    )
                ) {
                    viewModel.playingEpisode?.let {
                        EpisodePlayer(
                            imageUrl = it.channel.imageUrl,
                            title = it.title,
                            exoPlayer = viewModel.exoPlayer,
                            onDismissed = { viewModel.clearPlayer() }
                        )
                    }
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