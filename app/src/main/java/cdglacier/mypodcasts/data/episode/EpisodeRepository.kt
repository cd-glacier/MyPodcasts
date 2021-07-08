package cdglacier.mypodcasts.data.episode

import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode

interface EpisodeRepository {
    suspend fun getEpisodes(channel: Channel): Result<List<Episode>>
}