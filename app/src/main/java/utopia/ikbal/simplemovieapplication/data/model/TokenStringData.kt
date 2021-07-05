package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class TokenStringData(
    @SerializedName("request_token")
    val requestToken : String
)