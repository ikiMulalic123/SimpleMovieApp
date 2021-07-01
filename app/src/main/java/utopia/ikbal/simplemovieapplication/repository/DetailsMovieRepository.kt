package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.RateMovieData
import utopia.ikbal.simplemovieapplication.network.DetailsApi

class DetailsMovieRepository
constructor(
    private val detailsApi: DetailsApi,
) {

    fun getDetails(movieId: Int) = detailsApi.getDetails(movieId, TOKEN)

    fun getCredits(movieId: Int) = detailsApi.getCredits(movieId, TOKEN)

    fun getReviews(movieId: Int, page: Int) = detailsApi.getReviews(movieId, TOKEN, page)

    fun getVideos(movieId: Int) = detailsApi.getVideos(movieId, TOKEN)

    fun getImages(movieId: Int) = detailsApi.getImages(movieId, TOKEN)

    fun rateMovie(movieId: Int, sessionId: String, body: RateMovieData) =
        detailsApi.rateMovie(movieId, TOKEN, sessionId, body)
}