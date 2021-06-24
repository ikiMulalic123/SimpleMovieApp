package utopia.ikbal.simplemovieapplication.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import utopia.ikbal.simplemovieapplication.util.NetworkResultProcessor

open class BaseActivity : AppCompatActivity(), NetworkResultProcessor {
    override fun showGenericError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}