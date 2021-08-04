package cdglacier.mypodcasts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cdglacier.mypodcasts.data.channel.Channel

@Database(entities = [Channel::class], version = 1, exportSchema = false)
abstract class MyPodcastDatabase : RoomDatabase() {
    abstract val dao: MyPodcastDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MyPodcastDatabase? = null

        fun getInstance(context: Context): MyPodcastDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyPodcastDatabase::class.java,
                        "subscribed_channel_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return INSTANCE!!
            }
        }
    }
}