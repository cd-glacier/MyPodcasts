package cdglacier.mypodcasts.data.channel

import cdglacier.mypodcasts.model.Channel

interface ChannelRepository {
    suspend fun getSubscribedChannel(): Result<List<Channel>>
    suspend fun addSubscribedChannel(feedUrl: String): Result<Unit>
    suspend fun deleteSubscribedChannel(channel: Channel): Result<Unit>
    suspend fun getChannel(domain: String): Result<Channel>
}