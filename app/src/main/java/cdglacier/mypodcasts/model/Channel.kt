package cdglacier.mypodcasts.model

data class Channel(
    val domain: String,
    val name: String,
    val author: String?,
    val imageUrl: String,
    val description: String,
    val newFeedsUrl: String,
    val webSiteUrl: String
)