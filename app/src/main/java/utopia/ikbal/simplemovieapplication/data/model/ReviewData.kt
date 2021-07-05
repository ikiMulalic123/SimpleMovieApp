package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ReviewData (
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("author_details")
    var authorDetails : ReviewRatingData,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)