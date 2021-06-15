package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class ActorData(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("also_known_as")
    var also_known_as: List<Any>?,
    @SerializedName("biography")
    val biography: String? = null,
    @SerializedName("birthday")
    val birthday: String? = null,
    @SerializedName("deathday")
    val deathday: String? = null,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdb_id: String? = null,
    @SerializedName("known_for_department")
    val known_for_department: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("place_of_birth")
    val place_of_birth: String? = null,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("profile_path")
    val profile_path: String? = null
)