package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.data.model.RateMovieData
import utopia.ikbal.simplemovieapplication.network.DetailsApi

class DetailsMovieRepository
constructor(
    private val detailsApi: DetailsApi
) {

    fun getDetails(movieId: Int) = detailsApi.getDetails(movieId)

    fun getCredits(movieId: Int) = detailsApi.getCredits(movieId)

    fun getReviews(movieId: Int, page: Int) = detailsApi.getReviews(movieId, page)

    fun getVideos(movieId: Int) = detailsApi.getVideos(movieId)

    fun getImages(movieId: Int) = detailsApi.getImages(movieId)

    fun rateMovie(movieId: Int, sessionId: String, body: RateMovieData) =
        detailsApi.rateMovie(movieId, sessionId, body)
}