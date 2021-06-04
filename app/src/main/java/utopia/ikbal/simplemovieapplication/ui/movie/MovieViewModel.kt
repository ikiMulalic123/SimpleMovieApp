package utopia.ikbal.simplemovieapplication.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.MovieData
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.data.MovieList
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.network.MovieApi.Companion.LATEST
import utopia.ikbal.simplemovieapplication.network.MovieApi.Companion.POPULARITY
import utopia.ikbal.simplemovieapplication.network.MovieApi.Companion.RECENT
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.network.RetrofitProvider
import utopia.ikbal.simplemovieapplication.repository.MovieRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject constructor(private val movieRepository: MovieRepository): BaseViewModel() {

    private val _movieListLiveData = MutableLiveData<NetworkResult<List<MovieData>?>>()
    val movieListLiveData: LiveData<NetworkResult<List<MovieData>?>> = _movieListLiveData

    private var page: Int = 1
    private var totalPages: Int? = 1
    var loading: Boolean = false
    var isLastPage: Boolean = false

    fun loadInitial(movieFragmentType: MovieFragmentType) {
        page = 1
        addToDisposable(movieRepository.getMovies(movieFragmentType, page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _movieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                totalPages = it.totalPages
                _movieListLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (page == totalPages)
                page++
                loadAfter(movieFragmentType)
            }, {
                _movieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun loadAfter(movieFragmentType: MovieFragmentType) {
        addToDisposable(movieRepository.getMovies(movieFragmentType, page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                Log.d("Ikbal","Imamo problem")
                loading = true
                _movieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                Log.d("Ikbal"," Subscribe Imamo problem")
                _movieListLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (page == totalPages)
                page++
                loading = false
            }, {
                _movieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }
/*
    private fun getSingle(movieFragmentType: MovieFragmentType): Single<MovieList> {
        return when (movieFragmentType) {
            MovieFragmentType.LATEST -> RetrofitProvider.movieApi.getLatest(TOKEN, page, LATEST)
            MovieFragmentType.RECENT -> RetrofitProvider.movieApi.getRecent(TOKEN, page, RECENT)
            MovieFragmentType.POPULAR -> RetrofitProvider.movieApi.getPopular(
                TOKEN,
                page,
                POPULARITY
            )
        }
    }*/
}