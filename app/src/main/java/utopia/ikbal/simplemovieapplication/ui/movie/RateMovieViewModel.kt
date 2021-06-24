package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.RateMovieData
import utopia.ikbal.simplemovieapplication.data.model.RateMovieResponseData
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class RateMovieViewModel
@Inject
constructor(
    private val detailsMovieRepository: DetailsMovieRepository,
) : BaseViewModel() {

    private val _rateMovieLiveData = MutableLiveData<NetworkResult<RateMovieResponseData>?>()

    val rateMovieLiveData: LiveData<NetworkResult<RateMovieResponseData>?> = _rateMovieLiveData

    fun rateMovie(movieId: Int, sessionId: String, body: RateMovieData) {
        addToDisposable(detailsMovieRepository.rateMovie(movieId, sessionId, body)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _rateMovieLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _rateMovieLiveData.value = NetworkResult.Data(it)
            }, {
                _rateMovieLiveData.value = NetworkResult.Error(it)
            })
        )
    }
}