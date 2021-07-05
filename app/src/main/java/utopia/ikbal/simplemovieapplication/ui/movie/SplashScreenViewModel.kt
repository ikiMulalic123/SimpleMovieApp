package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.SharedPreferenceRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel
@Inject
constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) : BaseViewModel() {

    private val _sharedPreferenceBooleanLiveData = MutableLiveData<NetworkResult<Boolean>>()

    val sharedPreferenceBooleanLiveData: LiveData<NetworkResult<Boolean>> =
        _sharedPreferenceBooleanLiveData

    fun getBooleanFromSharedPreference() {
        addToDisposable(sharedPreferenceRepository.getBooleanFromSharedPreference()
            .applySchedulers(scheduler)
            .subscribe({
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Error(it)
            }))
    }
}