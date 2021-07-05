package utopia.ikbal.simplemovieapplication.util

import android.content.Context
import android.widget.Toast

class ToastUtil() {

    companion object {

        fun showShortToast(context: Context, message: CharSequence) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(context: Context, message: CharSequence) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}