package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.SessionData
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.LoginViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private val loginObserver = Observer<NetworkResult<SessionData>?> {
        processNetworkResult(
            it,
            data = { data -> checkIfLoginSucceeded(data) },
            onError = { showGenericError("Something went wrong") }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViewModel()
        initObserver()
        initLogin()
    }

    private fun getUsernameInput() = et_username.text.toString()

    private fun getPasswordInput() = et_password.text.toString()

    private fun initLogin() {
        btn_login.setOnClickListener {
            loginViewModel.login(getUsernameInput(), getPasswordInput())
        }
    }

    private fun checkIfLoginSucceeded(sessionData: SessionData) {
        if (sessionData.success == true) {
            sessionData.sessionId?.let { Log.d("Ikbal", it) }
            Toast.makeText(this, "You successfully logged in", Toast.LENGTH_SHORT).show()
            loginViewModel.saveBoolean(true)
            sessionData.sessionId?.let {
                Log.d("Ikbal","From sessionId, sessionId is $it")
                loginViewModel.saveString(it) }
            HomeActivity.launch(this)
        } else {
            Toast.makeText(this, "Login failed, please try again", Toast.LENGTH_SHORT).show()
        }
        et_username.text = null
        et_password.text = null
    }

    private fun initObserver() {
        with(loginViewModel) {
            sessionLiveData.observe(this@LoginActivity, loginObserver)
        }
    }

    private fun initViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    companion object {

        private fun createLaunchIntent(context: Context) =
            Intent(context, LoginActivity::class.java)

        fun launch(context: Context) =
            context.startActivity(createLaunchIntent(context))
    }
}