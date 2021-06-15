package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorSeriesData(
    @SerializedName("cast")
    val cast:List<ActorSeriesCastData>?,
    @SerializedName("crew")
    val crew:List<Any>?,
    @SerializedName("id")
    val id: Int
)