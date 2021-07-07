package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.data.model.MovieList
import utopia.ikbal.simplemovieapplication.network.MovieApi
import utopia.ikbal.simplemovieapplication.network.SearchMovieApi

class MovieRepository
constructor(
    private val movieApi: MovieApi,
    private val searchMovieApi: SearchMovieApi
) {

    fun getMovies(fragmentType: MovieFragmentType, page: Int): Single<MovieList> {
        return when (fragmentType) {
            MovieFragmentType.LATEST -> movieApi.getLatest(page, MovieApi.LATEST)
            MovieFragmentType.RECENT -> movieApi.getRecent(page, MovieApi.RECENT)
            MovieFragmentType.POPULAR -> movieApi.getPopular(page, MovieApi.POPULARITY)
        }
    }

    fun getSearchedMovies(page: Int, query: String): Single<MovieList> {
        return searchMovieApi.getFilteredMovies(page, query)
    }
}