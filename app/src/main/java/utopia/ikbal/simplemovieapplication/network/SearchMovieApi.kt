package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import utopia.ikbal.simplemovieapplication.data.MovieList

interface SearchMovieApi {
    @GET(MOVIE)
    fun getFilteredMovies(
        @Query(API_KEY) token: String,
        @Query(PAGE) page: Int,
        @Query(QUERY) filter: String,
    ): Single<MovieList>

    companion object {
        private const val QUERY = "query"
        private const val PAGE = "page"
        private const val MOVIE = "movie"
        private const val API_KEY = "api_key"
    }
}