package utopia.ikbal.simplemovieapplication.data.model

import com.google.gson.annotations.SerializedName

data class RateMovieResponseData(
    @SerializedName("status_code")
    val status_code: Int,
    @SerializedName("status_message")
    val status_message: String
)