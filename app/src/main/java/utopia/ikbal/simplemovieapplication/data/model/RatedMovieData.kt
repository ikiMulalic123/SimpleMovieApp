package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class RatedMovieData(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Float,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("rating")
    var rating: Float
)