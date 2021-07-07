package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class RateMovieResponseData(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String
)