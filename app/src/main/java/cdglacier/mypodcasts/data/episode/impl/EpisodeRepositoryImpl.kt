package cdglacier.mypodcasts.data.episode.impl

import android.os.Build
import androidx.annotation.RequiresApi
import cdglacier.mypodcasts.data.MyPodcastDatabaseDao
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import dev.stalla.PodcastRssParser
import dev.stalla.model.atom.AtomPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepositoryImpl(
    private val database: MyPodcastDatabaseDao
) : EpisodeRepository {
    override suspend fun getEpisodes(channel: Channel): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            Result.success(database.getEpisodes(channel.id).map { it.translate(channel) })
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun storeNewEpisodes(channel: Channel): Result<Unit> =
        withContext(Dispatchers.IO) {
            val podcast = PodcastRssParser.parse(channel.newFeedsUrl)
            val episodeData = podcast?.episodes?.map { it.translate(channel.id) }

            episodeData?.forEach { database.upsertEpisode(it) }

            Result.success(Unit)
        }

    override suspend fun getSubscribedEpisodes(): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            val channels = database.getSubscribedChannels()

            Result.success(database.getEpisodes().mapNotNull { episode ->
                val channel = channels.find { it.id == episode.channelId }
                if (channel == null) {
                    null
                } else {
                    episode.translate(channel.translate())
                }
            })
        }

    override suspend fun getEpisode(channel: Channel, title: String): Result<Episode> =
        withContext(Dispatchers.IO) {
            Result.success(database.getEpisode(title).translate(channel))
        }
}

private fun List<AtomPerson>.join() = this.joinToString(", ")

@RequiresApi(Build.VERSION_CODES.O)
private fun dev.stalla.model.Episode.translate(channelId: Int) =
    cdglacier.mypodcasts.data.episode.Episode(
        mediaUrl = this.enclosure.url,
        channelId = channelId,
        title = this.title,
        description = this.description,
        publishedAt = this.pubDate?.toString(),
        lengthSecond = this.itunes?.duration?.rawDuration?.seconds,
        episodeWebSiteUrl = this.link,
    )

private fun cdglacier.mypodcasts.data.episode.Episode.translate(channel: Channel) = Episode(
    title = this.title,
    description = this.description,
    publishedAt = this.publishedAt,
    mediaUrl = requireNotNull(this.mediaUrl),
    lengthSecond = this.lengthSecond,
    episodeWebSiteUrl = this.episodeWebSiteUrl,
    channel = Episode.Channel(
        domain = channel.domain,
        name = channel.name,
        imageUrl = channel.imageUrl,
        author = channel.author
    )
)

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
