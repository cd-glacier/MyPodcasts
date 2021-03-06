package cdglacier.mypodcasts.data.episode

import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode

interface EpisodeRepository {
    suspend fun getEpisodes(channel: Channel): Result<List<Episode>>
    suspend fun storeNewEpisodes(channel: Channel): Result<Unit>
    suspend fun getSubscribedEpisodes(): Result<List<Episode>>
    suspend fun getEpisode(channel: Channel, title: String): Result<Episode>
}