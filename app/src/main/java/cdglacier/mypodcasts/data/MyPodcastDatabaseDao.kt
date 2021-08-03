package cdglacier.mypodcasts.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cdglacier.mypodcasts.model.Episode

@Dao
interface MyPodcastDatabaseDao {
    @Query("SELECT * FROM channels")
    fun getSubscribedChannels(): LiveData<List<Episode.Channel>>

    @Insert
    fun insertSubscribedChannel(channel: Episode.Channel)
   
    @Delete
    fun deleteSubscribedChannel(channel: Episode.Channel)
}