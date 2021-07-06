package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class VideoList(
    @SerializedName("id")
    val id: Float,
    @SerializedName("results")
    var results: List<VideoData>
)