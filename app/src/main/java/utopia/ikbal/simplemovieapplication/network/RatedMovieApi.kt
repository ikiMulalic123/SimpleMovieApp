package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieList

interface RatedMovieApi {

    @GET(RATED)
    fun getRatedMovies(
        @Query(API_KEY) token: String,
        @Query(SESSION_ID) sessionId: String,
        @Query(PAGE) page: Int
    ): Single<RatedMovieList>

    companion object {
        private const val PAGE = "page"
        private const val API_KEY = "api_key"
        private const val SESSION_ID = "session_id"
        private const val ACCOUNT = "account/"
        private const val ACCOUNT_ID = "account_id"
        private const val RATED = "$ACCOUNT{$ACCOUNT_ID}/rated/movies"
    }
}