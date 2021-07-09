package cdglacier.mypodcasts.data.podcast

import cdglacier.mypodcasts.model.Channel

interface ChannelRepositery {
    suspend fun getSubscribedChannel(): Result<List<Channel>>
}