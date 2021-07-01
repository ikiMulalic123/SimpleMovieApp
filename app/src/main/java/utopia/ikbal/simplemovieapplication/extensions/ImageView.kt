package utopia.ikbal.simplemovieapplication.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import utopia.ikbal.simplemovieapplication.R

fun ImageView.load(url: String, requestManager: RequestManager? = null) {
    requestManager?.load(url)?.placeholder(R.color.glide_place_holder)?.into(this)
        ?: run {
            Glide.with(this).load(url).into(this)
        }
}

fun ImageView.loadImageWithPlaceholder(
    url: String,
    requestManager: RequestManager?,
) {
    requestManager?.load(url)?.listener(object : RequestListener<Drawable> {
        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean,
        ): Boolean {
            this@loadImageWithPlaceholder.visible()
            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean,
        ): Boolean {
            return false
        }
    })?.override(Target.SIZE_ORIGINAL)?.diskCacheStrategy(DiskCacheStrategy.DATA)?.into(this)
}

