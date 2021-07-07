package cdglacier.mypodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    Surface(color = MaterialTheme.colors.background) {
        Text("my podcasts app")
    }
}


@Preview(showBackground = true)
@Composable
fun MyPodcastsAppPreview() {
    MyPodcastsTheme {
        MyPodcastsApp()
    }
}