package cdglacier.mypodcasts.data

import androidx.room.*
import cdglacier.mypodcasts.data.channel.Channel

@Dao
interface MyPodcastDatabaseDao {
    @Query("SELECT * FROM channels")
    suspend fun getSubscribedChannels(): List<Channel>

    @Query("SELECT * FROM channels where domain = :domain")
    suspend fun getChannel(domain: String): Channel

    @Insert
    suspend fun insertSubscribedChannel(channel: Channel)

    @Update
    suspend fun updateChannel(channel: Channel)

    @Delete
    suspend fun deleteSubscribedChannel(channel: Channel)
}