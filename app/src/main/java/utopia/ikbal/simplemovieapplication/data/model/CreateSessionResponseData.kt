package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class CreateSessionResponseData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String
)