package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.*
import utopia.ikbal.simplemovieapplication.data.model.*

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

    @GET(VIDEOS)
    fun getVideos(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String
    ): Single<VideoList>

    @GET(IMAGES)
    fun getImages(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String
    ): Single<ImageList>

    @POST(RATING)
    fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) token: String,
        @Query(SESSION) sessionId: String,
        @Body body: RateMovieData
    ): Single<RateMovieResponseData>

    companion object {
        private const val API_KEY = "api_key"
        private const val MOVIE = "movie/"
        private const val MOVIE_ID = "movie_id"
        private const val PAGE = "page"
        private const val SESSION = "session_id"
        private const val DETAILS = "$MOVIE{$MOVIE_ID}"
        private const val CREDITS = "$DETAILS/credits"
        private const val REVIEWS = "$DETAILS/reviews"
        private const val VIDEOS = "$DETAILS/videos"
        private const val IMAGES = "$DETAILS/images"
        private const val RATING = "$DETAILS/rating"
    }
}