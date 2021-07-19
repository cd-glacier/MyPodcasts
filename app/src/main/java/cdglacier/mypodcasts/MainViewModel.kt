package cdglacier.mypodcasts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Episode
import kotlinx.coroutines.launch

class MainViewModel(
    private val channelRepository: ChannelRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private var _playingEpisode by mutableStateOf<Episode?>(null)
    val playingEpisode: Episode?
        get() = _playingEpisode

    private val _latestEpisodes = MutableLiveData<List<Episode>>()
    val latestEpisodes: LiveData<List<Episode>>
        get() = _latestEpisodes

    fun updatePlayingEpisode(episode: Episode) {
        _playingEpisode = episode
    }

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