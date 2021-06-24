package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.MY_SHARED_PREFERENCE

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSharedPreference()
        checkIfUserIsLogged()
        setContentView(R.layout.activity_splash_screen)
    }

    private fun initSharedPreference() {
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    private fun checkIfUserIsLogged() {
        if (!sharedPreferences.getBoolean("isUserLoggedIn", false))
            LoginActivity.launch(this)
        else HomeActivity.launch(this)
    }
}