package utopia.ikbal.simplemovieapplication.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.SplashScreenViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity() {

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private val sharedPreferenceObserver = Observer<NetworkResult<Boolean>>() {
        processNetworkResult(
            it,
            data = { data ->
                checkIfUserIsLogged(data)
            },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initViewModel()
        initObserver()
    }

    private fun initViewModel() {
        splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        with(splashScreenViewModel) {
            getBooleanFromSharedPreference()
        }
    }

    private fun initObserver() {
        with(splashScreenViewModel) {
            sharedPreferenceBooleanLiveData.observe(this@SplashScreenActivity,
                sharedPreferenceObserver)
        }
    }

    private fun checkIfUserIsLogged(isUserLogged: Boolean) {
        if (!isUserLogged) {
            finish()
            LoginActivity.launch(this)
        } else {
            finish()
            HomeActivity.launch(this)
        }
    }
}