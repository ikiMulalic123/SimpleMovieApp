package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.data.MovieList
import utopia.ikbal.simplemovieapplication.network.MovieApi
import utopia.ikbal.simplemovieapplication.network.SearchMovieApi

class MovieRepository
constructor(
    private val movieApi: MovieApi,
    private val searchMovieApi: SearchMovieApi
) {

    fun getMovies(fragmentType: MovieFragmentType, page: Int): Single<MovieList> {
        return when (fragmentType) {
            MovieFragmentType.LATEST -> movieApi.getLatest(TOKEN, page,
                MovieApi.LATEST)
            MovieFragmentType.RECENT -> movieApi.getRecent(TOKEN, page,
                MovieApi.RECENT)
            MovieFragmentType.POPULAR -> movieApi.getPopular(
                TOKEN,
                page,
                MovieApi.POPULARITY
            )
        }
    }

    fun getSearchedMovies(page: Int, query: String): Single<MovieList> {
        return searchMovieApi.getFilteredMovies(TOKEN, page,query)
    }
}