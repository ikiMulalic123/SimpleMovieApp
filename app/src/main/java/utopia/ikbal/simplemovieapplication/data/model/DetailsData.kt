package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class DetailsData(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String? = null,
    @SerializedName("belongs_to_collection")
    val belongs_to_collection: Any?,
    @SerializedName("budget")
    val budget: Float,
    @SerializedName("genres")
    var genres: List<Any>?,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdb_id: String? = null,
    @SerializedName("original_language")
    val original_language: String? = null,
    @SerializedName("original_title")
    val original_title: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Float,
    @SerializedName("poster_path")
    val poster_path: String? = null,
    @SerializedName("production_companies")
    var production_companies: List<Any>?,
    @SerializedName("production_countries")
    var production_countries: List<Any>?,
    @SerializedName("release_date")
    val release_date: String? = null,
    @SerializedName("revenue")
    val revenue: Float,
    @SerializedName("runtime")
    val runtime: Float,
    @SerializedName("spoken_languages")
    var spoken_languages: List<Any>?,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val vote_average: Float,
    @SerializedName("vote_count")
    val vote_count: Float,
)