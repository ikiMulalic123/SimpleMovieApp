package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.CreditsData
import utopia.ikbal.simplemovieapplication.data.model.DetailsData
import utopia.ikbal.simplemovieapplication.data.model.ReviewList
import utopia.ikbal.simplemovieapplication.data.model.VideoList
import utopia.ikbal.simplemovieapplication.network.DetailsApi

class DetailsMovieRepository
constructor(
    private val detailsApi: DetailsApi
) {

    fun getDetails(movie_id: Int): Single<DetailsData> =
        detailsApi.getDetails(movie_id, TOKEN)


    fun getCredits(movie_id: Int): Single<CreditsData> =
        detailsApi.getCredits(movie_id, TOKEN)


    fun getReviews(movie_id: Int, page: Int): Single<ReviewList> =
        detailsApi.getReviews(movie_id, TOKEN, page)


    fun getVideos(movie_id: Int): Single<VideoList> =
        detailsApi.getVideos(movie_id, TOKEN)

}