package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.network.ActorApi

class ActorMovieRepository
constructor(
    private val actorApi: ActorApi
) {

    fun getActorDetails(actorId: Int) = actorApi.getActorDetails(actorId)

    fun getActorMovies(actorId: Int) = actorApi.getActorMovies(actorId)

    fun getActorSeries(actorId: Int) = actorApi.getActorSeries(actorId)

    fun getActorImages(actorId: Int) = actorApi.getActorImages(actorId)
}