package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ImageList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrops")
    val backdrops : List<ImageData>,
    @SerializedName("posters")
    val posters : List<ImageData>
)