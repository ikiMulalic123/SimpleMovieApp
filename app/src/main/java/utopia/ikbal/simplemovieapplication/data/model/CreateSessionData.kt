package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class CreateSessionData(
    @SerializedName("access_token")
    val accessToken : String
)