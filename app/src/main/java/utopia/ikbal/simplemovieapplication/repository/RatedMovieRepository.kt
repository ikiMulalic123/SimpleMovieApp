package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieList
import utopia.ikbal.simplemovieapplication.network.RatedMovieApi

class RatedMovieRepository
constructor(
    private val ratedMovieApi: RatedMovieApi,
) {

    fun getRatedMovies(page: Int, sessionId: String): Single<RatedMovieList> {
        return ratedMovieApi.getRatedMovies(TOKEN, sessionId, page)
    }
}