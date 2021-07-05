package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorMovieCategory(
    @SerializedName("cast")
    val cast:List<ActorMovieData>?,
    @SerializedName("crew")
    val crew:List<ActorProducerData>?,
    @SerializedName("id")
    val id: Int
)