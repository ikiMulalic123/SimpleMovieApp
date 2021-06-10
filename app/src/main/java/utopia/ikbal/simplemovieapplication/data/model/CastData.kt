package utopia.ikbal.simplemovieapplication.data.model

data class CastData(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int?,
    val known_for_department: String? = null,
    val name: String? = null,
    val original_name: String? = null,
    val popularity: Float?,
    val profile_path: String? = null,
    val cast_id: Int?,
    val character: String? = null,
    val credit_id: String? = null,
    val order: Float?
)