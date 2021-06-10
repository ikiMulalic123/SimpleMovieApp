package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class CreditsData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<CastData>?,
    @SerializedName("crew")
    val crew: List<Any>?
)
