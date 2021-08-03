package cdglacier.mypodcasts.data.channel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "domain")
    val domain: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "new_feed_url")
    val newFeedsUrl: String,
)
