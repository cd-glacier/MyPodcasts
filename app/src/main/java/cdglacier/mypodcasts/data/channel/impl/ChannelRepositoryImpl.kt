package cdglacier.mypodcasts.data.channel.impl

import android.os.Build
import androidx.annotation.RequiresApi
import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.model.Channel
import dev.stalla.PodcastRssParser
import dev.stalla.model.atom.AtomPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl(
    private val database: MyPodcastDatabaseDao,
) : ChannelRepository {
    override suspend fun getSubscribedChannels(): Result<List<Channel>> =
        withContext(Dispatchers.IO) {
            Result.success(database.getSubscribedChannels().map { it.translate() })
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun storeSubscribedChannelFromWeb(): Result<Unit> =
        withContext(Dispatchers.IO) {
            val channels = database.getSubscribedChannels()

            val notStoredChannels = channels.filter { it.name == null }
            notStoredChannels.forEach {
                val channel = fetchChanel(it.newFeedsUrl).getOrThrow()
                channel.id = it.id
                database.upsertChannel(channel)
            }

            Result.success(Unit)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addSubscribedChannel(feedUrl: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            val channel = fetchChanel(feedUrl).getOrThrow()

            Result.success(database.upsertChannel(channel))
        }

    override suspend fun deleteSubscribedChannel(channel: Channel): Result<Unit> =
        withContext(Dispatchers.IO) {
            database.deleteChannel(channel.translate())
            database.deleteChannelEpisodes(channel.id)
           
            Result.success(Unit)
        }

    override suspend fun getChannel(domain: String): Result<Channel> =
        withContext(Dispatchers.IO) {
            Result.success(database.getChannel(domain).translate())
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchChanel(newFeedsUrl: String): Result<cdglacier.mypodcasts.data.channel.Channel> =
        withContext(Dispatchers.IO) {
            val podcast = PodcastRssParser.parse(newFeedsUrl)
            if (podcast != null) {
                Result.success(
                    cdglacier.mypodcasts.data.channel.Channel(
                        id = null,
                        domain = podcast.link.removePrefix("https://").removePrefix("http://"),
                        name = podcast.title,
                        author = podcast.atom?.authors?.join() ?: podcast.itunes?.author,
                        imageUrl = podcast.image?.url
                            ?: podcast.itunes?.image?.href,
                        description = podcast.description,
                        newFeedsUrl = newFeedsUrl,
                        webSiteUrl = podcast.link,
                    )
                )
            } else {
                Result.failure(Throwable("Podcast not found"))
            }
        }
}

private fun List<AtomPerson>.join(): String? = if (this.isNotEmpty()) {
    this.joinToString(", ")
} else {
    null
}

private fun cdglacier.mypodcasts.data.channel.Channel.translate() =
    Channel(
        id = requireNotNull(this.id),
        domain = this.domain ?: "",
        name = this.name ?: "",
        author = this.author,
        imageUrl = this.imageUrl,
        description = this.description,
        newFeedsUrl = this.newFeedsUrl,
        webSiteUrl = this.webSiteUrl
    )

private fun Channel.translate() = cdglacier.mypodcasts.data.channel.Channel(
    id = this.id,
    domain = this.domain,
    name = this.name,
    author = this.author,
    imageUrl = this.imageUrl,
    description = this.description,
    newFeedsUrl = this.newFeedsUrl,
    webSiteUrl = this.webSiteUrl
)
