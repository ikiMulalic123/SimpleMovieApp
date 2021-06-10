package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import utopia.ikbal.simplemovieapplication.data.model.MovieList

interface MovieApi {
    @GET(MOVIE)
    fun getLatest(
        @Query(API_KEY) token: String,
        @Query(PAGE) page: Int,
        @Query(SORT_BY) latest: String
    ): Single<MovieList>

    @GET(MOVIE)
    fun getRecent(
        @Query(API_KEY) token: String,
        @Query(PAGE) page: Int,
        @Query(SORT_BY) release_date: String
    ): Single<MovieList>

    @GET(MOVIE)
    fun getPopular(
        @Query(API_KEY) token: String,
        @Query(PAGE) page: Int,
        @Query(SORT_BY) popularity: String
    ): Single<MovieList>

    companion object {
        private const val API_KEY = "api_key"
        private const val PAGE = "page"
        private const val MOVIE = "movie"
        private const val SORT_BY = "sort_by"
        const val LATEST = "release_date.asc"
        const val RECENT = "release_date.desc"
        const val POPULARITY = "popularity.desc"
    }
}