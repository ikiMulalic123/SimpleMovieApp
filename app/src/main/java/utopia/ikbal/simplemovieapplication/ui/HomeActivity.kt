package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    companion object {

        private fun createLaunchIntent(context: Context) =
            Intent(context, HomeActivity::class.java)

        fun launch(context: Context) =
            context.startActivity(createLaunchIntent(context))
    }
}