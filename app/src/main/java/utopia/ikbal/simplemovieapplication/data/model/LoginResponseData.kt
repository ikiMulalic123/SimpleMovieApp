package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("expires_at")
    val expiresAt: String? = null,
    @SerializedName("request_token")
    val requestToken: String? = null
)