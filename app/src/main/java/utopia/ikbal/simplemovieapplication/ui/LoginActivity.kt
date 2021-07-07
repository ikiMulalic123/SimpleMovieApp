package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.LoginViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel

    private val loginObserver = Observer<NetworkResult<Unit>> {
        processNetworkResult(
            it,
            onLoaded = { HomeActivity.launch(this)},
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViewModel()
        initObserver()
        initClickListener()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> loginViewModel.login(getUsernameInput(), getPasswordInput())
        }
    }

    private fun getUsernameInput() = et_username.text.toString()

    private fun getPasswordInput() = et_password.text.toString()

    private fun initClickListener() {
        btn_login.setOnClickListener(this)
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