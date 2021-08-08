package cdglacier.mypodcasts.data.episode.impl

import android.os.Build
import androidx.annotation.RequiresApi
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import dev.stalla.PodcastRssParser
import dev.stalla.model.atom.AtomPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepositoryImpl : EpisodeRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEpisodes(channel: Channel): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            val podcast = PodcastRssParser.parse(channel.newFeedsUrl)

            if (podcast != null) {
                val episodes = podcast.episodes.map {
                    Episode(
                        title = it.title,
                        description = it.description,
                        publishedAt = it.pubDate?.toString(),
                        mediaUrl = it.enclosure.url,
                        lengthSecond = it.itunes?.duration?.rawDuration?.seconds,
                        episodeWebSiteUrl = it.link,
                        channel = Episode.Channel(
                            domain = podcast.link.removePrefix("https://")
                                .removePrefix("http://"),
                            name = podcast.title,
                            imageUrl = podcast.image?.url
                                ?: podcast.itunes?.image?.href,
                            author = podcast.atom?.authors?.join()
                                ?: podcast.itunes?.author,
                        )
                    )
                }

                Result.success(episodes)
            } else {
                Result.failure(Throwable("Podcast not found"))
            }
        }

    override suspend fun getEpisode(channel: Channel, title: String): Result<Episode> {
        TODO("Not yet implemented")
    }
}

private fun List<AtomPerson>.join() = this.joinToString(", ")
