package utopia.ikbal.simplemovieapplication.data.model

data class ReviewData (
    val author: String? = null,
    var author_details : ReviewRatingData,
    val content: String? = null,
    val created_at: String? = null,
    val id: String? = null,
    val updated_at: String? = null,
    val url: String? = null
)