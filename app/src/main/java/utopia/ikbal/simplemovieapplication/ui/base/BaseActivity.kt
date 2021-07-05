package utopia.ikbal.simplemovieapplication.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import utopia.ikbal.simplemovieapplication.util.NetworkResultProcessor
import utopia.ikbal.simplemovieapplication.util.ToastUtil

open class BaseActivity : AppCompatActivity(), NetworkResultProcessor {
    override fun showGenericError(message: String) {
        ToastUtil.showShortToast(this,message)
    }
}