package cdglacier.mypodcasts.data.channel.impl

import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val fakeChannels = listOf<Channel>(
    Channel(
        id = 1,
        domain = "rebuild.fm",
        name = "Rebuild",
        author = "Tatsuhiko Miyagawa",
        imageUrl = "https://cdn.rebuild.fm/images/icon240.png",
        description = "ウェブ開発、プログラミング、モバイル、ガジェットなどにフォーカスしたテクノロジー系ポッドキャストです。 #rebuildfm",
        newFeedsUrl = "https://feeds.rebuild.fm/rebuildfm",
        webSiteUrl = "https://rebuild.fm",
    ),
    Channel(
        id = 2,
        domain = "talkingkotlin.com",
        name = "Talking Kotlin",
        author = null,
        imageUrl = "https://i1.sndcdn.com/avatars-000289370353-di6ese-original.jpg",
        description = "A bimonthly podcast that covers the Kotlin programming language by JetBrains, as well as related technologies. Hosted by Hadi Hariri",
        newFeedsUrl = "https://feeds.soundcloud.com/users/soundcloud:users:280353173/sounds.rss",
        webSiteUrl = "http://talkingkotlin.com",
    )
)

class FakeChannelRepositoryImpl : ChannelRepository {
    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            Result.success(fakeChannels)
        }

    override suspend fun addSubscribedChannel(feedUrl: String): Result<Unit> = Result.success(Unit)

    override suspend fun deleteSubscribedChannel(channel: Channel): Result<Unit> =
        Result.success(Unit)

    override suspend fun getChannel(domain: String): Result<Channel> =
        withContext(Dispatchers.IO) {
            val channel = fakeChannels.find { it.domain == domain }
            if (channel == null) {
                Result.failure(Throwable("channel not found"))
            } else {
                Result.success(channel)
            }
        }
}