package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorImageList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profiles")
    val profiles : List<ImageData>
)