package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class CastData(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("character")
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    @SerializedName("order")
    val order: Float?
)