package utopia.ikbal.simplemovieapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import utopia.ikbal.simplemovieapplication.network.ActorApi
import utopia.ikbal.simplemovieapplication.network.DetailsApi
import utopia.ikbal.simplemovieapplication.network.MovieApi
import utopia.ikbal.simplemovieapplication.network.SearchMovieApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val BASE_URL = "https://api.themoviedb.org/3/discover/"
    private val BASE_SEARCH_URL = "https://api.themoviedb.org/3/search/"
    private val BASE_DETAILS_MOVIE_URL = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit.Builder): MovieApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchMovieService(retrofit: Retrofit.Builder): SearchMovieApi {
        return retrofit
            .baseUrl(BASE_SEARCH_URL)
            .build()
            .create(SearchMovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailsMovieService(retrofit: Retrofit.Builder): DetailsApi {
        return retrofit
            .baseUrl(BASE_DETAILS_MOVIE_URL)
            .build()
            .create(DetailsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideActorMovieService(retrofit: Retrofit.Builder): ActorApi {
        return retrofit
            .baseUrl(BASE_DETAILS_MOVIE_URL)
            .build()
            .create(ActorApi::class.java)
    }
}