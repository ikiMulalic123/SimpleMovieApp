package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.MovieData
import utopia.ikbal.simplemovieapplication.data.MovieList
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.network.RetrofitProvider
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult

class SearchMovieViewModel : BaseViewModel() {

    private val _movieListLiveData = MutableLiveData<NetworkResult<List<MovieData>?>>()
    val movieListLiveData: LiveData<NetworkResult<List<MovieData>?>> = _movieListLiveData
    var loading: Boolean = false
    var page: Int = 1
    var totalPages: Int? = 1
    var isLastPage: Boolean = false
    var query: String = ""

    fun loadInitial(query: String) {
        page = 1
        this.query = query
        addToDisposable(getSingle()
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _movieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                totalPages = it.totalPages
                if (it.results?.isEmpty() == true)
                    _movieListLiveData.value = NetworkResult.Empty
                else {
                    _movieListLiveData.value = NetworkResult.Data(it.results)
                    isLastPage = (page == totalPages)
                    page++
                    loadAfter()
                }
            }, {
                _movieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun loadAfter() {
        addToDisposable(getSingle()
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _movieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _movieListLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (page == totalPages)
                loading = false
                page++
            }, {
                _movieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    private fun getSingle(): Single<MovieList> {
        return RetrofitProvider.searchMovieApi.getFilteredMovies(TOKEN, page, query)
    }
}