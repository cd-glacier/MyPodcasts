package cdglacier.mypodcasts.data.channel

import cdglacier.mypodcasts.model.Channel

interface ChannelRepository {
    suspend fun getSubscribedChannel(): Result<List<Channel>>
    suspend fun getChannel(domain: String): Result<Channel>
}