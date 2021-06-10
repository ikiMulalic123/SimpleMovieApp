package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import utopia.ikbal.simplemovieapplication.data.model.CreditsData
import utopia.ikbal.simplemovieapplication.data.model.DetailsData
import utopia.ikbal.simplemovieapplication.data.model.ReviewList

interface DetailsApi {

    @GET(DETAILS)
    fun getDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String
    ): Single<DetailsData>

    @GET(CREDITS)
    fun getCredits(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String
    ): Single<CreditsData>

    @GET(REVIEWS)
    fun getReviews(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String,
        @Query(PAGE) page: Int
    ): Single<ReviewList>

    companion object {
        private const val API_KEY = "api_key"
        private const val MOVIE = "movie/"
        private const val MOVIE_ID = "movie_id"
        private const val PAGE = "page"
        private const val DETAILS = "$MOVIE{$MOVIE_ID}"
        private const val CREDITS = "$DETAILS/credits"
        private const val REVIEWS = "$DETAILS/reviews"
    }
}