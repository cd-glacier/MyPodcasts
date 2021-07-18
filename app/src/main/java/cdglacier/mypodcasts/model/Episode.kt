package cdglacier.mypodcasts.model

data class Episode(
    val title: String,
    val description: String?,
    val publishedAt: String,
    val mediaUrl: String,
    val lengthSecond: Long,
    val episodeWebSiteUrl: String?,

    val channel: Channel
) {
    data class Channel(
        val name: String,
        val imageUrl: String?,
    )
}