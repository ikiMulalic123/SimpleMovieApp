package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorImageCategory(
    @SerializedName("id")
    val id: Float?,
    @SerializedName("profiles")
    val profiles: List<ActorImageData>?
)