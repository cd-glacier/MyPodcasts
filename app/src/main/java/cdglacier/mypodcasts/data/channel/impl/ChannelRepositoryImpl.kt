package cdglacier.mypodcasts.data.channel.impl

import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.httpclient.HttpClient
import cdglacier.mypodcasts.model.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl(
    private val database: MyPodcastDatabaseDao,
) : ChannelRepository {
    private val httpClient = HttpClient()

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

    private suspend fun fetchChanel(newFeedUrl: String): Result<Channel> {
        val parser = Parser.Builder()
            .build()

        return withContext(Dispatchers.IO) {
            val channel = parser.getChannel(newFeedUrl)

            Result.success(
                Channel(
                    domain = channel.link?.removePrefix("https://")?.removePrefix("http://") ?: "",
                    name = channel.title ?: "",
                    author = channel.articles.firstOrNull()?.author,
                    imageUrl = channel.image?.url ?: channel.articles.firstOrNull()?.image,
                    description = channel.description,
                    newFeedsUrl = newFeedUrl,
                    webSiteUrl = channel.link
                )
            )
        }
    }
}