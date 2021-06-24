package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.LoginRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginRepository: LoginRepository,
) : BaseViewModel() {

    private val _tokenLiveData = MutableLiveData<NetworkResult<TokenData>?>()
    private val _sessionLiveData = MutableLiveData<NetworkResult<SessionData>?>()
    private val _validateTokenLiveData = MutableLiveData<NetworkResult<LoginResponseData>?>()
    private val _sessionWithLoginLiveData =
        MutableLiveData<NetworkResult<CreateSessionResponseData>>()


    val tokenLiveData: LiveData<NetworkResult<TokenData>?> = _tokenLiveData
    val sessionLiveData: LiveData<NetworkResult<SessionData>?> = _sessionLiveData
    val validateTokenLiveData: LiveData<NetworkResult<LoginResponseData>?> = _validateTokenLiveData
    val sessionWithLoginLiveData: LiveData<NetworkResult<CreateSessionResponseData>?> =
        _sessionWithLoginLiveData

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
}