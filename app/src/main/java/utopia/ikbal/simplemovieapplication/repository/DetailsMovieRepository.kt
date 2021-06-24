package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.network.DetailsApi
import utopia.ikbal.simplemovieapplication.util.SharedPreferenceRepo

class DetailsMovieRepository
constructor(
    private val detailsApi: DetailsApi) {

    fun getDetails(movieId: Int): Single<DetailsData> =
        detailsApi.getDetails(movieId, TOKEN)

    fun getCredits(movieId: Int): Single<CreditsData> =
        detailsApi.getCredits(movieId, TOKEN)

    fun getReviews(movieId: Int, page: Int): Single<ReviewList> =
        detailsApi.getReviews(movieId, TOKEN, page)

    fun getVideos(movieId: Int): Single<VideoList> =
        detailsApi.getVideos(movieId, TOKEN)

    fun rateMovie(movieId: Int,sessionId: String, body: RateMovieData) : Single<RateMovieResponseData> =
        detailsApi.rateMovie(movieId,TOKEN,sessionId, body)
}