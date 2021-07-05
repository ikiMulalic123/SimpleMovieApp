package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.RateMovieData
import utopia.ikbal.simplemovieapplication.data.model.RateMovieResponseData
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.repository.SharedPreferenceRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class RateMovieViewModel
@Inject
constructor(
    private val detailsMovieRepository: DetailsMovieRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) : BaseViewModel() {

    private val _rateMovieLiveData = MutableLiveData<NetworkResult<RateMovieResponseData>>()
    private val _sharedPreferenceStringLiveData = MutableLiveData<NetworkResult<String>>()
    private val _sharedPreferenceBooleanLiveData = MutableLiveData<NetworkResult<Boolean>>()

    val rateMovieLiveData: LiveData<NetworkResult<RateMovieResponseData>> = _rateMovieLiveData
    val sharedPreferenceStringLiveData: LiveData<NetworkResult<String>> =
        _sharedPreferenceStringLiveData
    val sharedPreferenceBooleanLiveData: LiveData<NetworkResult<Boolean>> =
        _sharedPreferenceBooleanLiveData

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

    fun saveStringToSharedPreference(name: String) {
        addToDisposable(sharedPreferenceRepository.saveStringToSharedPreference(name).subscribe())
    }

    fun saveBooleanToSharedPreference(name: Boolean) {
        addToDisposable(sharedPreferenceRepository.saveBooleanToSharedPreference(name).subscribe())
    }

    fun getStringFromSharedPreference() {
        addToDisposable(sharedPreferenceRepository.getStringFromSharedPreference()
            .subscribe({
                _sharedPreferenceStringLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceStringLiveData.value = NetworkResult.Error(it)
            }))
    }

    fun getBooleanFromSharedPreference() {
        addToDisposable(sharedPreferenceRepository.getBooleanFromSharedPreference()
            .subscribe({
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Error(it)
            }))
    }

    fun clearFromSharedPreference() {
        addToDisposable(sharedPreferenceRepository.clearFromSharedPreference().subscribe())
    }
}