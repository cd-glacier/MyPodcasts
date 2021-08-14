package cdglacier.mypodcasts.data

import androidx.room.*
import cdglacier.mypodcasts.data.channel.Channel
import cdglacier.mypodcasts.data.episode.Episode

@Dao
interface MyPodcastDatabaseDao {
    @Query("SELECT * FROM channels")
    suspend fun getSubscribedChannels(): List<Channel>

    @Query("SELECT * FROM channels where domain = :domain")
    suspend fun getChannel(domain: String): Channel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertChannel(channel: Channel)

    @Delete
    suspend fun deleteChannel(channel: Channel)

    @Query("SELECT * fROM episodes WHERE channel_id = :channelId")
    suspend fun getEpisodes(channelId: Int): List<Episode>

    @Query("SELECT * FROM episodes ORDER BY published_at DESC")
    suspend fun getEpisodes(): List<Episode>

    @Query("SELECT * from episodes WHERE title = :title")
    suspend fun getEpisode(title: String): Episode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEpisode(episode: Episode)

    @Query("DELETE FROM episodes where channel_id = :channelId")
    suspend fun deleteChannelEpisodes(channelId: Int)
}