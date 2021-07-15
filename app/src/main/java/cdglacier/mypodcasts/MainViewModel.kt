package cdglacier.mypodcasts

import androidx.lifecycle.*
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Episode
import kotlinx.coroutines.launch

class MainViewModel(
    private val channelRepository: ChannelRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val _latestEpisodes = MutableLiveData<List<Episode>>(listOf())
    val latestEpisodes: LiveData<List<Episode>>
        get() = _latestEpisodes

    fun refetchLatestEpisodes() {
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

    class Factory(
        private val channelRepository: ChannelRepository,
        private val episodeRepository: EpisodeRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(channelRepository, episodeRepository) as T
        }
    }
}