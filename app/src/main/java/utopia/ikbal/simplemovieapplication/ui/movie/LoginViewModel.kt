package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.LoginRepository
import utopia.ikbal.simplemovieapplication.repository.SharedPreferenceRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : BaseViewModel() {

    private val _sessionLiveData = MutableLiveData<NetworkResult<Unit>>()
    private val _sharedPreferenceStringLiveData = MutableLiveData<NetworkResult<String>>()

    val sessionLiveData: LiveData<NetworkResult<Unit>> = _sessionLiveData
    val sharedPreferenceStringLiveData: LiveData<NetworkResult<String>> =
        _sharedPreferenceStringLiveData

    fun login(username: String, password: String) {
        addToDisposable(loginRepository.getToken()
            .flatMap {
                loginRepository.validateTokenLogin(LoginData(username, password, it.requestToken))
            }
            .flatMap {
                it.requestToken?.let { it1 -> TokenStringData(it1) }?.let { it2 ->
                    loginRepository.createSession(it2)
                }
            }
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _sessionLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                if(it.success == true) {
                    saveBooleanToSharedPreference(true)
                    it.sessionId?.let { token -> saveStringToSharedPreference(token) }
                    _sessionLiveData.value = NetworkResult.Loaded
                } else {
                    _sessionLiveData.value = NetworkResult.Error(Throwable())
                }
            }, {
                _sessionLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    private fun saveStringToSharedPreference(name: String) {
        addToDisposable(sharedPreferenceRepository.saveStringToSharedPreference(name).subscribe())
    }

    private fun saveBooleanToSharedPreference(name: Boolean) {
        addToDisposable(sharedPreferenceRepository.saveBooleanToSharedPreference(name).subscribe())
    }
}