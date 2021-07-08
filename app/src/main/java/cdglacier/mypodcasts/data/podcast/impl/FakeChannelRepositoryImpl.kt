package cdglacier.mypodcasts.data.podcast.impl

import cdglacier.mypodcasts.data.podcast.ChannelRepositery
import cdglacier.mypodcasts.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

val fakeChannels = listOf<Channel>(
    Channel(
        name = "Rebuild",
        author = "Tatsuhiko Miyagawa",
        imageUrl = "https://cdn.rebuild.fm/images/icon240.png",
        description = "ウェブ開発、プログラミング、モバイル、ガジェットなどにフォーカスしたテクノロジー系ポッドキャストです。 #rebuildfm",
        webSiteUrl = "https://rebuild.fm"
    )
)

class FakeChannelRepositoryImpl : ChannelRepositery {
    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            delay(1500)
            Result.success(fakeChannels)
        }
}