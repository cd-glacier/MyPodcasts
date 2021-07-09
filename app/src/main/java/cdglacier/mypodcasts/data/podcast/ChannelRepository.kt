package cdglacier.mypodcasts.data.podcast

import cdglacier.mypodcasts.model.Channel

interface ChannelRepository {
    suspend fun getSubscribedChannel(): Result<List<Channel>>
}