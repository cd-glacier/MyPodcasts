package cdglacier.mypodcasts

import cdglacier.mypodcasts.data.channel.ChannelRepository
import cdglacier.mypodcasts.data.channel.impl.FakeChannelRepositoryImpl
import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.data.episode.impl.FakeEpisodeRepositoryImpl

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