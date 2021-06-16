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
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsActorData
import utopia.ikbal.simplemovieapplication.extensions.inflate
import utopia.ikbal.simplemovieapplication.extensions.load

class ActorMovieViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val image: ImageView = itemView.findViewById(R.id.img_actor_movie_poster)
    private val movieName: TextView = itemView.findViewById(R.id.tv_actor_movie_name)
    var requestManager: RequestManager? = null
    var movieClickListener: OnMovieClickListener? = null


    fun bind(actorMovieAsActorData: ActorMovieAsActorData) {
        itemView.setOnClickListener {
            actorMovieAsActorData.id.let { it1 -> movieClickListener?.onMovieClick(it1) }
        }
        movieName.text = actorMovieAsActorData.original_title
        image.load(Constants.BASE_IMAGE_URL + actorMovieAsActorData.poster_path, requestManager)
    }

    fun recycleView() {
        if (itemView.context != null && itemView.context is AppCompatActivity && !(itemView.context as AppCompatActivity).isDestroyed) {
            requestManager?.clear(image)
        }
    }
}