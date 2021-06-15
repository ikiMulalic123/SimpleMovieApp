package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.MovieData

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val requestManager = Glide.with(context)
    private val movieList = mutableListOf<MovieData>()

    var movieClickListener: OnMovieClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(R.layout.item_movie, parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.movieClickListener = movieClickListener
            holder.requestManager = requestManager
            holder.bind(it)
        }
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.recycleView()
    }

    override fun getItemCount() = movieList.size

    fun submitList(movies: List<MovieData>) {
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearList() {
        movieList.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): MovieData? {
        return if (position < movieList.size) movieList[position]
        else null
    }
}