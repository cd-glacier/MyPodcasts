package cdglacier.mypodcasts.data.channel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "new_feed_url")
    val newFeedsUrl: String,

    @ColumnInfo(name = "domain")
    val domain: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "web_site_url")
    val webSiteUrl: String?,
)
