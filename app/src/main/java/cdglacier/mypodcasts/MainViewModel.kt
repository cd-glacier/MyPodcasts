package cdglacier.mypodcasts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Episode
import kotlinx.coroutines.launch

class MainViewModel(
    private val channelRepository: ChannelRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    val _latestEpisodes = MutableLiveData<List<Episode>>(listOf())
    val latestEpisodes: LiveData<List<Episode>>
        get() = _latestEpisodes

    suspend fun refetchLatestEpisodes() {
        viewModelScope.launch {
            val subscribedChannels = channelRepository.getSubscribedChannel()

            val subscribedEpisods = subscribedChannels.map { channels ->
                channels
                    .map {
                        episodeRepository.getEpisodes(it).getOrNull()
                    }
                    .filterNotNull()
                    .flatten()
                    .sortedBy { it.publishedAt }
            }

            _latestEpisodes.value = subscribedEpisods.getOrThrow()
        }
    }
}