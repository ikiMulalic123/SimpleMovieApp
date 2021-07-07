package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieData

class RatedMovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val requestManager = Glide.with(context)
    private val ratedMovieList = mutableListOf<RatedMovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(R.layout.item_movie, parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        ratedMovieList[position].let {
            holder.requestManager = requestManager
            holder.bindRatedMovies(it)
        }
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        holder.recycleView()
    }

    override fun getItemCount() = ratedMovieList.size

    fun submitList(movies: List<RatedMovieData>) {
        ratedMovieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearList() {
        ratedMovieList.clear()
        notifyDataSetChanged()
    }
}