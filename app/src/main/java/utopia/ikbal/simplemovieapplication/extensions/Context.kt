package utopia.ikbal.simplemovieapplication.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun Context.checkIfIsNull(): Boolean {
    return (this != null && this is AppCompatActivity
            && !(this as AppCompatActivity).isDestroyed)
}