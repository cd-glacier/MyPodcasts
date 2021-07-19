package cdglacier.mypodcasts.data.channel.impl

import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val fakeChannels = listOf<Channel>(
    Channel(
        name = "Rebuild",
        author = "Tatsuhiko Miyagawa",
        imageUrl = "https://cdn.rebuild.fm/images/icon240.png",
        description = "ウェブ開発、プログラミング、モバイル、ガジェットなどにフォーカスしたテクノロジー系ポッドキャストです。 #rebuildfm",
        newFeedsUrl = "https://feeds.soundcloud.com/users/soundcloud:users:280353173/sounds.rss",
        webSiteUrl = "https://rebuild.fm"
    ),
    Channel(
        name = "Talking Kotlin",
        author = null,
        imageUrl = "https://i1.sndcdn.com/avatars-000289370353-di6ese-original.jpg",
        description = "A bimonthly podcast that covers the Kotlin programming language by JetBrains, as well as related technologies. Hosted by Hadi Hariri",
        newFeedsUrl = "https://feeds.soundcloud.com/users/soundcloud:users:280353173/sounds.rss",
        webSiteUrl = "http://talkingkotlin.com"
    )
)

class FakeChannelRepositoryImpl : ChannelRepository {
    override suspend fun getSubscribedChannel(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            Result.success(fakeChannels)
        }
}