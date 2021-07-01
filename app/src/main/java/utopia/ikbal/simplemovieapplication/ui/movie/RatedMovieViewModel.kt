package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieData
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.RatedMovieRepository
import utopia.ikbal.simplemovieapplication.repository.SharedPreferenceRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class RatedMovieViewModel
@Inject
constructor(
    private val ratedMovieRepository: RatedMovieRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) : BaseViewModel() {

    private val _ratedMovieListLiveData = MutableLiveData<NetworkResult<List<RatedMovieData>?>>()
    private val _sharedPreferenceStringLiveData = MutableLiveData<NetworkResult<String>?>()

    private var page: Int = 1
    private var totalPages: Int? = 1

    val ratedMovieListLiveData: LiveData<NetworkResult<List<RatedMovieData>?>> =
        _ratedMovieListLiveData
    val sharedPreferenceStringLiveData: LiveData<NetworkResult<String>?> =
        _sharedPreferenceStringLiveData

    var loading: Boolean = false
    var isLastPage: Boolean = false

    fun getRatedMovies() {
        page = 1
        addToDisposable(sharedPreferenceRepository.getString()
            .flatMapSingle {
                (ratedMovieRepository.getRatedMovies(page, it))
            }
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _ratedMovieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                totalPages = it.totalPages
                _ratedMovieListLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let { page <= it } == true)
                page++
                loadAfter()
            }, {
                _ratedMovieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun loadAfter() {
        addToDisposable(sharedPreferenceRepository.getString()
            .flatMapSingle {
                ratedMovieRepository.getRatedMovies(page, it)
            }
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _ratedMovieListLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _ratedMovieListLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let {page <= it  } == true)
                page++
                loading = false
            }, {
                _ratedMovieListLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun saveString(name: String) {
        addToDisposable(sharedPreferenceRepository.saveString(name).subscribe())
    }

    fun getString() {
        addToDisposable(sharedPreferenceRepository.getString()
            .subscribe({
                _sharedPreferenceStringLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceStringLiveData.value = NetworkResult.Error(it)
            }))
    }

    fun clear() {
        addToDisposable(sharedPreferenceRepository.clear().subscribe())
    }
}