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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.*
import cdglacier.mypodcasts.data.MyPodcastDatabase
import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.impl.ChannelRepositoryImpl
import cdglacier.mypodcasts.data.episode.impl.FakeEpisodeRepositoryImpl
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import cdglacier.mypodcasts.ui.channel.ChannelScreen
import cdglacier.mypodcasts.ui.channel.detail.ChannelDetailScreen
import cdglacier.mypodcasts.ui.component.EpisodePlayer
import cdglacier.mypodcasts.ui.component.HeaderTabRow
import cdglacier.mypodcasts.ui.episode.detail.EpisodeDetailScreen
import cdglacier.mypodcasts.ui.home.HomeScreen
import cdglacier.mypodcasts.ui.setting.SettingScreen
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        val channelRepository = ChannelRepositoryImpl(database)
        val episodeRepository = FakeEpisodeRepositoryImpl()

        val factory = MainViewModel.Factory(exoPlayer, channelRepository, episodeRepository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private val database: MyPodcastDatabaseDao by lazy {
        MyPodcastDatabase.getInstance(applicationContext).dao
    }

    private val exoPlayer: ExoPlayer by lazy {
        SimpleExoPlayer.Builder(this).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.refetchLatestEpisodes()
        viewModel.refetchSubscribedChannels()

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
    val subscribedChannels: List<Channel>? by viewModel.subscribedChannels.observeAsState(null)

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
                        episodeItemOnClick = { episode ->
                            navController.navigate("${MyPodcastsScreen.Channel.name}/${episode.channel.domain}/${episode.title}")
                        },
                        playButtonOnClick = { viewModel.updatePlayingEpisode(it) },
                        titleOnClick = { channel -> navController.navigate("${MyPodcastsScreen.Channel.name}/${channel.domain}") }
                    )
                }

                composable(MyPodcastsScreen.Channel.name) {
                    ChannelScreen(
                        subscribedChannel = subscribedChannels,
                        channelOnClick = { channel -> navController.navigate("${MyPodcastsScreen.Channel.name}/${channel.domain}") }
                    )
                }

                composable(
                    route = "${MyPodcastsScreen.Channel.name}/{domain}",
                    arguments = listOf(
                        navArgument("domain") {
                            type = NavType.StringType
                        }
                    )
                ) { entry ->
                    val domain = entry.arguments?.getString("domain")
                    domain?.let {
                        viewModel.fetchChannelDetail(it)
                    }

                    ChannelDetailScreen(
                        channel = viewModel.channelDetail.first,
                        episodes = viewModel.channelDetail.second,
                        episodeItemOnClick = { episode ->
                            navController.navigate("${MyPodcastsScreen.Channel.name}/${episode.channel.domain}/${episode.title}")
                        },
                        playButtonOnClick = { viewModel.updatePlayingEpisode(it) }
                    )
                }

                composable(
                    route = "${MyPodcastsScreen.Channel.name}/{domain}/{title}",
                    arguments = listOf(
                        navArgument("domain") {
                            type = NavType.StringType
                        },
                        navArgument("title") {
                            type = NavType.StringType
                        }
                    )
                ) { entry ->
                    val domain = entry.arguments?.getString("domain")
                    val title = entry.arguments?.getString("title")

                    if (domain != null && title != null) {
                        viewModel.fetchEpisodeDetail(domain, title)
                    }

                    EpisodeDetailScreen(
                        episode = viewModel.episodeDetail,
                        playButtonOnClick = {
                            viewModel.updatePlayingEpisode(
                                requireNotNull(
                                    viewModel.episodeDetail
                                )
                            )
                        }
                    )
                }

                composable(MyPodcastsScreen.Setting.name) {
                    SettingScreen()
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