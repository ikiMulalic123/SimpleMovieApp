package utopia.ikbal.simplemovieapplication.data.model

data class ImageData(
    val aspect_ratio : Float?,
    val file_path : String? = null,
    val height : Int?,
    val iso_639_1 : String? = null,
    val vote_average : Float?,
    val vote_count : Int?,
    val width : Int?
)