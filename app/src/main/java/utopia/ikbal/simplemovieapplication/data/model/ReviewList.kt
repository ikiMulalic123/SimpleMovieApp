package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ReviewList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ReviewData>,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("total_results")
    val total_results: Int?
)