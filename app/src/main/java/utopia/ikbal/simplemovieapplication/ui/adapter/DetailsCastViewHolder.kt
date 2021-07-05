package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.model.CastData
import utopia.ikbal.simplemovieapplication.extensions.checkIfIsNull
import utopia.ikbal.simplemovieapplication.extensions.inflate
import utopia.ikbal.simplemovieapplication.extensions.load

class DetailsCastViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val image: ImageView = itemView.findViewById(R.id.img_details_movie_poster)
    private val castName: TextView = itemView.findViewById(R.id.tv_details_cast_names)
    var requestManager: RequestManager? = null
    var castClickListener: OnMovieClickListener? = null

    fun bind(castData: CastData) {
        itemView.setOnClickListener {
            castData.id?.let { castClickListener?.onMovieClick(it) }
        }
        castName.text = castData.name
        image.load(Constants.BASE_IMAGE_URL + castData.profilePath, requestManager)
    }

    fun recycleView() {
        if (itemView.context.checkIfIsNull()) requestManager?.clear(image)
    }
}