package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorMovieData(
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("character")
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    @SerializedName("order")
    val order: Float?
)