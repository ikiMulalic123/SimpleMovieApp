package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.MovieData
import java.util.*

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieViewHolder>(), Filterable {

    var movieClickListener: OnMovieClickListener? = null
    private val requestManager = Glide.with(context)
    private val movieList = mutableListOf<MovieData>()
    private var movieFilterList = movieList

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                movieFilterList = if(constraint?.isEmpty() == true) {
                    movieList
                } else {
                    val resultList = mutableListOf<MovieData>()
                    for (row in movieList) {
                        if(row.original_title?.toLowerCase(Locale.ROOT)?.contains(constraint.toString()
                                .toLowerCase(Locale.ROOT)) == true) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilterList = results?.values as MutableList<MovieData>
                notifyDataSetChanged()
            }

        }
    }

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