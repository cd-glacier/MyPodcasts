package cdglacier.mypodcasts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Episode
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.launch

class MainViewModel(
    val exoPlayer: ExoPlayer,
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

        val mediaItem = MediaItem.fromUri(episode.mediaUrl)
        exoPlayer.apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    fun clearPlayer() {
        _playingEpisode = null

        exoPlayer.apply {
            clearMediaItems()
        }
    }

    fun refetchLatestEpisodes() {
        viewModelScope.launch {
            val subscribedChannels = channelRepository.getSubscribedChannel()

            val subscribedEpisodes = subscribedChannels.map { channels ->
                channels
                    .map {
                        episodeRepository.getEpisodes(it).getOrNull()
                    }
                    .filterNotNull()
                    .flatten()
                    .sortedBy { it.publishedAt }
            }

            _latestEpisodes.value = subscribedEpisodes.getOrThrow()
        }
    }

    fun invalidate() {
        _playingEpisode = null

        exoPlayer.apply {
            pause()
            clearMediaItems()
            release()
        }
    }

    class Factory(
        private val exoPlayer: ExoPlayer,
        private val channelRepository: ChannelRepository,
        private val episodeRepository: EpisodeRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(exoPlayer, channelRepository, episodeRepository) as T
        }
    }
}