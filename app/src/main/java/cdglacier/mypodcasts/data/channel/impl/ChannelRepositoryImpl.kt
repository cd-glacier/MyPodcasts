package cdglacier.mypodcasts.data.channel.impl

import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.httpclient.HttpClient
import cdglacier.mypodcasts.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request

class ChannelRepositoryImpl(
    private val database: MyPodcastDatabaseDao,
) : ChannelRepository {
    private val httpClient = HttpClient()

    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            val newFeedsUrls = database.getSubscribedChannels().map {
                it.newFeedsUrl
            }

            // TODO: fetch channel info from newFeedUrl
            fetchChanel(newFeedsUrls.first())

            Result.success(listOf()) // FIXME
        }

    override suspend fun getChannel(domain: String): Result<Channel> {
        TODO("Not yet implemented")
    }

    private suspend fun fetchChanel(newFeedUrl: String): Result<Channel> {
        val request = Request.Builder()
            .url(newFeedUrl)
            .build()

        return withContext(Dispatchers.IO) {
            val body = httpClient.getInstance().newCall(request = request).execute().body?.string()

            // TODO: parse rss
            Result.success(fakeChannels.first())
        }
    }
}