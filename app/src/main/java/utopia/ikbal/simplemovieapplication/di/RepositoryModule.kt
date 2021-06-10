package utopia.ikbal.simplemovieapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import utopia.ikbal.simplemovieapplication.network.DetailsApi
import utopia.ikbal.simplemovieapplication.network.MovieApi
import utopia.ikbal.simplemovieapplication.network.SearchMovieApi
import utopia.ikbal.simplemovieapplication.repository.DetailsRepository
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
    ): MovieRepository {
        return MovieRepository(movieApi, searchMovieApi)
    }

    @Singleton
    @Provides
    fun provideDetailsRepository(
        detailsApi: DetailsApi
    ): DetailsRepository {
        return DetailsRepository(detailsApi)
    }
}