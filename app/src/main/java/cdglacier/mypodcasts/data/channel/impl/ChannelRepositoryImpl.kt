package cdglacier.mypodcasts.data.channel.impl

import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl(
    private val database: MyPodcastDatabaseDao
) : ChannelRepository {
    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            val newFeedsUrl = database.getSubscribedChannels().map {
                it.newFeedsUrl
            }

            // TODO: fetch channel info from newFeedUrl

            Result.success(listOf()) // FIXME
        }

    override suspend fun getChannel(domain: String): Result<Channel> {
        TODO("Not yet implemented")
    }
}