package cdglacier.mypodcasts.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cdglacier.mypodcasts.data.channel.Channel

@Dao
interface MyPodcastDatabaseDao {
    @Query("SELECT * FROM channels")
    suspend fun getSubscribedChannels(): List<Channel>

    @Insert
    suspend fun insertSubscribedChannel(channel: Channel)

    @Delete
    suspend fun deleteSubscribedChannel(channel: Channel)
}