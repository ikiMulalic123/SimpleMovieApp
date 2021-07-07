package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class RatedMovieList(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<RatedMovieData>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)