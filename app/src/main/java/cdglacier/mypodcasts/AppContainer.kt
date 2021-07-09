package cdglacier.mypodcasts

import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.data.episode.FakeEpisodeRepositoryImpl
import cdglacier.mypodcasts.data.podcast.ChannelRepository
import cdglacier.mypodcasts.data.podcast.impl.FakeChannelRepositoryImpl

interface AppContainer {
    val channelRepository: ChannelRepository
    val episodeRepository: EpisodeRepository
}

class AppContainerImpl : AppContainer {
    override val channelRepository: ChannelRepository by lazy {
        FakeChannelRepositoryImpl()
    }

    override val episodeRepository: EpisodeRepository by lazy {
        FakeEpisodeRepositoryImpl()
    }
}