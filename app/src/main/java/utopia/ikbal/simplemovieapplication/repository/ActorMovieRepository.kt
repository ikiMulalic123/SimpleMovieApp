package utopia.ikbal.simplemovieapplication.repository

import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.network.ActorApi

class ActorMovieRepository
constructor(
    private val actorApi: ActorApi
) {

    fun getActorDetails(actorId: Int) =
        actorApi.getActorDetails(actorId, TOKEN)

    fun getActorMovies(actorId: Int) =
        actorApi.getActorMovies(actorId, TOKEN)

    fun getActorSeries(actorId: Int) =
        actorApi.getActorSeries(actorId, TOKEN)

    fun getActorImages(actorId: Int) =
        actorApi.getActorImages(actorId, TOKEN)
}