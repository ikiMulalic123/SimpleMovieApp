package utopia.ikbal.simplemovieapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import utopia.ikbal.simplemovieapplication.network.*
import utopia.ikbal.simplemovieapplication.repository.*
import utopia.ikbal.simplemovieapplication.util.SharedPreferenceRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSharedPreferenceRepo(
        @ApplicationContext context: Context
    ) :SharedPreferenceRepo = SharedPreferenceRepo.create(context)

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

    @Singleton
    @Provides
    fun provideSharedPreferenceRepository(
        sharedPreferenceRepo: SharedPreferenceRepo
    ) = SharedPreferenceRepository(sharedPreferenceRepo)
}