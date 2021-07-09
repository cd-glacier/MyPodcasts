package cdglacier.mypodcasts.model

data class Channel(
    val name: String,
    val author: String,
    val imageUrl: String,
    val description: String,
    val newFeedsUrl: String,
    val webSiteUrl: String
)