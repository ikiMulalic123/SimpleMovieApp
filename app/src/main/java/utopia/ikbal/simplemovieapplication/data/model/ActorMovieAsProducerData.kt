package utopia.ikbal.simplemovieapplication.data.model

data class ActorMovieAsProducerData(
    val adult: Boolean?,
    val backdrop_path: String? = null,
    val genre_ids: List<Any>?,
    val id: Int?,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean?,
    val vote_average: Float?,
    val vote_count: Float?,
    val popularity: Float?,
    val credit_id: String? = null,
    val department: String? = null,
    val job: String? = null
)