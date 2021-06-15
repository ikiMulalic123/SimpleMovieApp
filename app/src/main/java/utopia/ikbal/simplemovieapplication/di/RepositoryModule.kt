package utopia.ikbal.simplemovieapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import utopia.ikbal.simplemovieapplication.network.ActorApi
import utopia.ikbal.simplemovieapplication.network.DetailsApi
import utopia.ikbal.simplemovieapplication.network.MovieApi
import utopia.ikbal.simplemovieapplication.network.SearchMovieApi
import utopia.ikbal.simplemovieapplication.repository.ActorMovieRepository
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        searchMovieApi: SearchMovieApi,
    ): MovieRepository = MovieRepository(movieApi, searchMovieApi)

    @Singleton
    @Provides
    fun provideDetailsRepository(
        detailsApi: DetailsApi,
    ): DetailsMovieRepository = DetailsMovieRepository(detailsApi)

    @Singleton
    @Provides
    fun provideActorRepository(
        actorApi: ActorApi,
    ): ActorMovieRepository = ActorMovieRepository(actorApi)
}