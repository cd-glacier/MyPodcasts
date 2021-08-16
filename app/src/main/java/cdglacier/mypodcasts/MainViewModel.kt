package cdglacier.mypodcasts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Channel
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

    private val _subscribedChannels = MutableLiveData<List<Channel>>()
    val subscribedChannels: LiveData<List<Channel>>
        get() = _subscribedChannels

    private var _channelDetail by mutableStateOf<Pair<Channel?, List<Episode>?>>(Pair(null, null))
    val channelDetail: Pair<Channel?, List<Episode>?>
        get() = _channelDetail

    private var _episodeDetail by mutableStateOf<Episode?>(null)
    val episodeDetail: Episode?
        get() = _episodeDetail

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

    fun refetchLatestEpisodes() =
        viewModelScope.launch {
            channelRepository.storeSubscribedChannelFromWeb()
            val channels = channelRepository.getSubscribedChannels().getOrThrow()
            channels.forEach {
                episodeRepository.storeNewEpisodes(it)
            }

            val episodes = episodeRepository.getSubscribedEpisodes().getOrThrow()
            _latestEpisodes.value = episodes
        }

    fun refetchSubscribedChannels() =
        viewModelScope.launch {
            channelRepository.storeSubscribedChannelFromWeb()
            _subscribedChannels.value = channelRepository.getSubscribedChannels().getOrThrow()
        }

    fun addSubscribedChannel(feedUrl: String) =
        viewModelScope.launch {
            channelRepository.addSubscribedChannel(feedUrl)

            refetchSubscribedChannels()
            refetchLatestEpisodes()
        }

    fun removeSubscribedChannel(channel: Channel) =
        viewModelScope.launch {
            channelRepository.deleteSubscribedChannel(channel)

            refetchSubscribedChannels()
            refetchLatestEpisodes()
        }

    fun fetchChannelDetail(domain: String) =
        viewModelScope.launch {
            val channel = channelRepository.getChannel(domain).getOrThrow()
            episodeRepository.storeNewEpisodes(channel)
            val episodes = episodeRepository.getEpisodes(channel)

            _channelDetail = Pair(channel, episodes.getOrThrow())
        }

    fun fetchEpisodeDetail(domain: String, title: String) =
        viewModelScope.launch {
            val channel = channelRepository.getChannel(domain)
            val episode = episodeRepository.getEpisode(channel.getOrThrow(), title)

            _episodeDetail = episode.getOrThrow()
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