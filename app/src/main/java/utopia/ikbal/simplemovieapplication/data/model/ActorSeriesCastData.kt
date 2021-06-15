package utopia.ikbal.simplemovieapplication.data.model

data class ActorSeriesCastData(
    val backdrop_path: String? = null,
    val first_air_date: String? = null,
    val genre_ids: List<Any>?,
    val id: Float?,
    val name: String? = null,
    val origin_country: List<Any>?,
    val original_language: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val vote_average: Float?,
    val vote_count: Float?,
    val popularity: Float?,
    val character: String? = null,
    val credit_id: String? = null,
    val episode_count: Float?
)