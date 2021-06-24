package utopia.ikbal.simplemovieapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import utopia.ikbal.simplemovieapplication.network.*
import utopia.ikbal.simplemovieapplication.repository.ActorMovieRepository
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.repository.LoginRepository
import utopia.ikbal.simplemovieapplication.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        searchMovieApi: SearchMovieApi
    ) = MovieRepository(movieApi, searchMovieApi)

    @Singleton
    @Provides
    fun provideDetailsRepository(
        detailsApi: DetailsApi
    ) = DetailsMovieRepository(detailsApi)

    @Singleton
    @Provides
    fun provideActorRepository(
        actorApi: ActorApi,
    ) = ActorMovieRepository(actorApi)

    @Singleton
    @Provides
    fun provideLoginRepository(
        loginApi: LoginApi
    ) = LoginRepository(loginApi)

}