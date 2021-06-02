package utopia.ikbal.simplemovieapplication.data

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieData>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("errors")
    val errors: List<String>?
)