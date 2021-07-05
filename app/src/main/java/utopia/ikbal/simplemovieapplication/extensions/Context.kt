package utopia.ikbal.simplemovieapplication.extensions

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun Context.checkIfIsNull(): Boolean {
    val itemView: View? = null
    return (itemView?.context != null && itemView.context is AppCompatActivity
            && !(itemView.context as AppCompatActivity).isDestroyed)
}