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
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) : BaseViewModel() {

    private val _tokenLiveData = MutableLiveData<NetworkResult<TokenData>?>()
    private val _sessionLiveData = MutableLiveData<NetworkResult<SessionData>?>()
    private val _validateTokenLiveData = MutableLiveData<NetworkResult<LoginResponseData>?>()
    private val _sessionWithLoginLiveData =
        MutableLiveData<NetworkResult<CreateSessionResponseData>>()
    private val _sharedPreferenceStringLiveData = MutableLiveData<NetworkResult<String>?>()
    private val _sharedPreferenceBooleanLiveData = MutableLiveData<NetworkResult<Boolean>?>()

    val tokenLiveData: LiveData<NetworkResult<TokenData>?> = _tokenLiveData
    val sessionLiveData: LiveData<NetworkResult<SessionData>?> = _sessionLiveData
    val validateTokenLiveData: LiveData<NetworkResult<LoginResponseData>?> = _validateTokenLiveData
    val sessionWithLoginLiveData: LiveData<NetworkResult<CreateSessionResponseData>?> =
        _sessionWithLoginLiveData
    val sharedPreferenceStringLiveData: LiveData<NetworkResult<String>?> =
        _sharedPreferenceStringLiveData
    val sharedPreferenceBooleanLiveData: LiveData<NetworkResult<Boolean>?> =
        _sharedPreferenceBooleanLiveData
    var loading: Boolean = false

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
                _sessionLiveData.value = NetworkResult.Data(it)
            }, {
                _sessionLiveData.value = NetworkResult.Error(it)
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