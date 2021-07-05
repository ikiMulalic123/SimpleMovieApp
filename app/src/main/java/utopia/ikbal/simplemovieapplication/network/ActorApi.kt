package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorImageList
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieCategory
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesData

interface ActorApi {

    @GET(DETAILS)
    fun getActorDetails(
        @Path(PERSON_ID) personId: Int
    ): Single<ActorData>

    @GET(MOVIES)
    fun getActorMovies(
        @Path(PERSON_ID) personId: Int
    ): Single<ActorMovieCategory>

    @GET(SERIES)
    fun getActorSeries(
        @Path(PERSON_ID) personId: Int
    ): Single<ActorSeriesData>

    @GET(IMAGES)
    fun getActorImages(
        @Path(PERSON_ID) personId: Int
    ): Single<ActorImageList>

    companion object {
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