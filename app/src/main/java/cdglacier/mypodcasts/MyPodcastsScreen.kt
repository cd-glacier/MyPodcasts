package cdglacier.mypodcasts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class MyPodcastsScreen(
    val icon: ImageVector,
) {
    Home(
        icon = Icons.Filled.Home,
    ),
    Podcast(
        icon = Icons.Filled.Podcasts,
    ),
    Setting(
        icon = Icons.Filled.Settings,
    );

    companion object {
        fun fromRoute(route: String?): MyPodcastsScreen =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                Podcast.name -> Podcast
                Setting.name -> Setting
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
