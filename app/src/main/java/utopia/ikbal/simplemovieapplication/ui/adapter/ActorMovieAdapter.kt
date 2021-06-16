package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsActorData

class ActorMovieAdapter(private val context: Context) :
    RecyclerView.Adapter<ActorMovieViewHolder>() {

    private val requestManager = Glide.with(context)
    private val moviesList = mutableListOf<ActorMovieAsActorData>()

    var movieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder =
        ActorMovieViewHolder(R.layout.item_actor_movies, parent)

    override fun onBindViewHolder(holder: ActorMovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.movieClickListener = movieClickListener
            holder.requestManager = requestManager
            holder.bind(it)
        }
    }

    override fun getItemCount() = moviesList.size

    override fun onViewRecycled(holder: ActorMovieViewHolder) {
        super.onViewRecycled(holder)
        holder.recycleView()
    }

    fun submitList(movies: List<ActorMovieAsActorData>) {
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearList() {
        moviesList.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ActorMovieAsActorData? {
        return if (position < moviesList.size) moviesList[position]
        else null
    }
}