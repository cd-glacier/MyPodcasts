package cdglacier.mypodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cdglacier.mypodcasts.ui.component.HeaderTabRow
import cdglacier.mypodcasts.ui.home.HomeScreen
import cdglacier.mypodcasts.ui.theme.MyPodcastsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPodcastsTheme {
                MyPodcastsApp()
            }
        }
    }
}

@Composable
private fun MyPodcastsApp() {
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

                composable(MyPodcastsScreen.Podcast.name) {
                    Text(MyPodcastsScreen.Podcast.name)
                }

                composable(MyPodcastsScreen.Setting.name) {
                    Text(MyPodcastsScreen.Setting.name)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyPodcastsAppPreview() {
    MyPodcastsTheme {
        MyPodcastsApp()
    }
}