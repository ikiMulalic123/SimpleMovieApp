package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorMovieCategory(
    @SerializedName("cast")
    val cast:List<ActorMovieAsActorData>?,
    @SerializedName("crew")
    val crew:List<ActorMovieAsProducerData>?,
    @SerializedName("id")
    val id: Int
)