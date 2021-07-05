package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieData
import utopia.ikbal.simplemovieapplication.extensions.checkIfIsNull
import utopia.ikbal.simplemovieapplication.extensions.inflate
import utopia.ikbal.simplemovieapplication.extensions.load

class ActorMovieViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val image: ImageView = itemView.findViewById(R.id.img_actor_movie_poster)
    private val movieName: TextView = itemView.findViewById(R.id.tv_actor_movie_name)
    var requestManager: RequestManager? = null
    var movieClickListener: OnMovieClickListener? = null

    fun bind(actorMovieData: ActorMovieData) {
        itemView.setOnClickListener {
            actorMovieData.id.let { movieClickListener?.onMovieClick(it) }
        }
        movieName.text = actorMovieData.originalTitle
        image.load(Constants.BASE_IMAGE_URL + actorMovieData.posterPath, requestManager)
    }

    fun recycleView() {
        if (itemView.context.checkIfIsNull()) requestManager?.clear(image)
    }
}