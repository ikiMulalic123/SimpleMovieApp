package utopia.ikbal.simplemovieapplication.data.model

data class MovieData (
    var adult : Boolean,
    var backdrop_path: String? = null,
    var genre_ids : List<Int>,
    var id : Int,
    var original_language: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity : Float,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var video : Boolean,
    var vote_average : Float,
    var vote_count : Int
)