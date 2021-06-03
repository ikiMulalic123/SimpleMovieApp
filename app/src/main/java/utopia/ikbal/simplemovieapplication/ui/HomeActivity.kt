package utopia.ikbal.simplemovieapplication.ui

import android.os.Bundle
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}