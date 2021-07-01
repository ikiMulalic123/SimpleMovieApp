package utopia.ikbal.simplemovieapplication.ui.movie

import android.util.Log
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

    private val _sharedPreferenceStringLiveData = MutableLiveData<NetworkResult<String>?>()
    private val _sharedPreferenceBooleanLiveData = MutableLiveData<NetworkResult<Boolean>?>()

    val sharedPreferenceStringLiveData: LiveData<NetworkResult<String>?> =
        _sharedPreferenceStringLiveData
    val sharedPreferenceBooleanLiveData: LiveData<NetworkResult<Boolean>?> =
        _sharedPreferenceBooleanLiveData

    fun saveString(name: String) {
        addToDisposable(sharedPreferenceRepository.saveString(name).subscribe())
    }

    fun saveBoolean(name: Boolean) {
        addToDisposable(sharedPreferenceRepository.saveBoolean(name).subscribe())
    }

    fun getString() {
        addToDisposable(sharedPreferenceRepository.getString()
            .applySchedulers(scheduler)
            .subscribe({
                _sharedPreferenceStringLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceStringLiveData.value = NetworkResult.Error(it)
            }))
    }

    fun getBoolean() {
        addToDisposable(sharedPreferenceRepository.getBoolean()
            .applySchedulers(scheduler)
            .subscribe({
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Data(it)
            }, {
                _sharedPreferenceBooleanLiveData.value = NetworkResult.Error(it)
            }))
    }

    fun clear() {
        addToDisposable(sharedPreferenceRepository.clear().subscribe())
    }
}