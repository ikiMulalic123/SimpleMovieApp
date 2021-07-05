package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("site")
    val site: String? = null,
    @SerializedName("size")
    val size: Float,
    @SerializedName("type")
    val type: String? = null
)