package cdglacier.mypodcasts.data.channel.impl

import android.os.Build
import androidx.annotation.RequiresApi
import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.httpclient.HttpClient
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import dev.stalla.PodcastRssParser
import dev.stalla.model.atom.AtomPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl(
    private val database: MyPodcastDatabaseDao,
) : ChannelRepository {
    private val httpClient = HttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            val newFeedsUrls = database.getSubscribedChannels().map {
                it.newFeedsUrl
            }

            println("----------feed url-----------")
            println(newFeedsUrls)

            val channels = newFeedsUrls.mapNotNull {
                fetchChanel(it).getOrNull()
            }

            println("----------channels-----------")
            println(channels)

            Result.success(channels)
        }

    override suspend fun getChannel(domain: String): Result<Channel> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchChanel(newFeedsUrl: String): Result<Channel> {

        return withContext(Dispatchers.IO) {
            val podcast = PodcastRssParser.parse(newFeedsUrl)

            if (podcast != null) {
                Result.success(
                    Channel(
                        domain = podcast.link.removePrefix("https://").removePrefix("http://"),
                        name = podcast.title,
                        author = podcast.atom?.authors?.join() ?: podcast.itunes?.author,
                        imageUrl = podcast.image?.url
                            ?: podcast.episodes.first().itunes?.image?.href,
                        description = podcast.description,
                        newFeedsUrl = newFeedsUrl,
                        webSiteUrl = podcast.link,
                        episodes = podcast.episodes.map {
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
                                        ?: podcast.episodes.first().itunes?.image?.href,
                                    author = podcast.atom?.authors?.join()
                                        ?: podcast.itunes?.author,
                                )
                            )
                        }
                    )
                )
            } else {
                Result.failure(Throwable("Podcast not found"))
            }
        }
    }
}

private fun List<AtomPerson>.join() = this.joinToString(", ")