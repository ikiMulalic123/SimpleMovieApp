package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorImageCategory
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieCategory
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesData

interface ActorApi {

    @GET(DETAILS)
    fun getActorDetails(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) token: String
    ): Single<ActorData>

    @GET(MOVIES)
    fun getActorMovies(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) token: String
    ): Single<ActorMovieCategory>

    @GET(SERIES)
    fun getActorSeries(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) token: String
    ): Single<ActorSeriesData>

    @GET(IMAGES)
    fun getActorImages(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) token: String
    ):Single<ActorImageCategory>

    companion object {
        private const val API_KEY = "api_key"
        private const val PERSON = "person/"
        private const val PERSON_ID = "person_id"
        private const val DETAILS = "$PERSON{$PERSON_ID}"
        private const val MOVIE_CREDITS = "movie_credits"
        private const val MOVIES = "$DETAILS/$MOVIE_CREDITS"
        private const val TV_CREDITS = "tv_credits"
        private const val SERIES = "$DETAILS/$TV_CREDITS"
        private const val IMAGE = "images"
        private const val IMAGES = "$DETAILS/$IMAGE"
    }
}