package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.CreditsData
import utopia.ikbal.simplemovieapplication.data.model.DetailsData
import utopia.ikbal.simplemovieapplication.data.model.ReviewList
import utopia.ikbal.simplemovieapplication.network.DetailsApi

class DetailsRepository
constructor(
    private val detailsApi: DetailsApi,
) {

    fun getDetails(movie_id: Int): Single<DetailsData> {
        return detailsApi.getDetails(movie_id, TOKEN)
    }

    fun getCredits(movie_id: Int): Single<CreditsData> {
        return detailsApi.getCredits(movie_id, TOKEN)
    }

    fun getReviews(movie_id: Int, page: Int): Single<ReviewList> {
        return detailsApi.getReviews(movie_id, TOKEN, page)
    }

}