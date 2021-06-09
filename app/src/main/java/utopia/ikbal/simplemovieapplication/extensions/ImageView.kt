package utopia.ikbal.simplemovieapplication.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import utopia.ikbal.simplemovieapplication.R

fun ImageView.load(url: String, requestManager: RequestManager?) {
    requestManager?.let {
        it.load(url).placeholder(R.color.glide_place_holder).into(this)
    } ?: run {
        Glide.with(this).load(url).into(this)
    }
}