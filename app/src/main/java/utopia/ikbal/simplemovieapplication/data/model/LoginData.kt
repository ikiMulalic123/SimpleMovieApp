package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("username")
    val username : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("request_token")
    val requestToken : String
)