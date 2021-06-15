package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieCategory
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesData
import utopia.ikbal.simplemovieapplication.network.ActorApi

class ActorMovieRepository
constructor(
    private val actorApi: ActorApi
) {

    fun getActorDetails(actorId: Int): Single<ActorData> =
        actorApi.getActorDetails(actorId, TOKEN)

    fun getActorMovies(actorId: Int): Single<ActorMovieCategory> =
        actorApi.getActorMovies(actorId, TOKEN)

    fun getActorSeries(actorId: Int): Single<ActorSeriesData> =
        actorApi.getActorSeries(actorId, TOKEN)
}