package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("aspect_ratio")
    val aspectRatio : Float?,
    @SerializedName("file_path")
    val filePath : String? = null,
    @SerializedName("height")
    val height : Int?,
    @SerializedName("iso_639_1")
    val iso : String? = null,
    @SerializedName("vote_average")
    val voteAverage : Float?,
    @SerializedName("vote_count")
    val voteCount : Int?,
    @SerializedName("width")
    val width : Int?
)