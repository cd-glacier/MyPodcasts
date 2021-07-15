package cdglacier.mypodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cdglacier.mypodcasts.data.channel.impl.FakeChannelRepositoryImpl
import cdglacier.mypodcasts.data.episode.FakeEpisodeRepositoryImpl
import cdglacier.mypodcasts.model.Episode
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
                MyPodcastsApp(viewModel.latestEpisodes)
            }
        }
    }
}

@Composable
private fun MyPodcastsApp(
    subScribedEpisodes: LiveData<List<Episode>>
) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = MyPodcastsScreen.fromRoute(
        backstackEntry.value?.destination?.route
    )

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
                Text("TODO: PODCASTS PLAYER")
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MyPodcastsScreen.Home.name
            ) {
                composable(MyPodcastsScreen.Home.name) {
                    HomeScreen()
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