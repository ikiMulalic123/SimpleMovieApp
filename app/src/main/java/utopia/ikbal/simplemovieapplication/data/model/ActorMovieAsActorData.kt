package utopia.ikbal.simplemovieapplication.data.model

data class ActorMovieAsActorData(
    val original_language: String? = null,
    val original_title: String? = null,
    val poster_path: String? = null,
    val video: Boolean?,
    val vote_average: Float?,
    val id: Int,
    val release_date: String? = null,
    val vote_count: Int?,
    val title: String? = null,
    val adult: Boolean?,
    val backdrop_path: String? = null,
    val overview: String? = null,
    val genre_ids: List<Any>?,
    val popularity: Float?,
    val character: String? = null,
    val credit_id: String? = null,
    val order: Float?
)