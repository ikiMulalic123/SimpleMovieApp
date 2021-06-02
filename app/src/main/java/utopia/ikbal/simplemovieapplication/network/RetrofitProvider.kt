package utopia.ikbal.simplemovieapplication.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private val BASE_URL = "https://api.themoviedb.org/3/discover/"
    private val BASE_SEARCH_URL = "https://api.themoviedb.org/3/search/"

    private val retrofitMovie: Retrofit = getRetrofit(BASE_URL)
    private val retrofitSearchMovie: Retrofit = getRetrofit(BASE_SEARCH_URL)

    val movieApi: MovieApi = retrofitMovie.create(MovieApi::class.java)
    val searchMovieApi: SearchMovieApi = retrofitSearchMovie.create(SearchMovieApi::class.java)

    private fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }
}