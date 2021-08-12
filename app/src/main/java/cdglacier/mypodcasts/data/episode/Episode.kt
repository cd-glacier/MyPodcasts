package cdglacier.mypodcasts.data.episode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey(autoGenerate = false)
    val mediaUrl: String,

    @ColumnInfo(name = "channel_id")
    val channelId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "published_at")
    val publishedAt: String?,

    @ColumnInfo(name = "length_second")
    val lengthSecond: Long?,

    @ColumnInfo(name = "episode_web_site_url")
    val episodeWebSiteUrl: String?,
)