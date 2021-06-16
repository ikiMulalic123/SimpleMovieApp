package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData
import utopia.ikbal.simplemovieapplication.extensions.inflate
import utopia.ikbal.simplemovieapplication.extensions.load

class ActorSeriesViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val image: ImageView = itemView.findViewById(R.id.img_actor_series_poster)
    private val seriesName: TextView = itemView.findViewById(R.id.tv_actor_series_name)
    var requestManager: RequestManager? = null
    var castClickListener: OnMovieClickListener? = null


    fun bind(actorSeriesCastData: ActorSeriesCastData) {
        itemView.setOnClickListener {
            actorSeriesCastData.id?.let { it1 -> castClickListener?.onMovieClick(it1) }
        }
        seriesName.text = actorSeriesCastData.original_name
        image.load(Constants.BASE_IMAGE_URL + actorSeriesCastData.poster_path, requestManager)
    }

    fun recycleView() {
        if (itemView.context != null && itemView.context is AppCompatActivity && !(itemView.context as AppCompatActivity).isDestroyed) {
            requestManager?.clear(image)
        }
    }
}