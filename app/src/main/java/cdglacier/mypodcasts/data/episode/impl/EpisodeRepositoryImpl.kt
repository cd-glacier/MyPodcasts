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
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEpisodes(channel: Channel): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            val podcast = PodcastRssParser.parse(channel.newFeedsUrl)
            val episodeData = podcast?.episodes?.map { it.translate(channel.id) }

            episodeData?.forEach { database.upsertEpisode(it) }

            Result.success(database.getEpisodes(channel.id).map { it.translate(channel) })
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
