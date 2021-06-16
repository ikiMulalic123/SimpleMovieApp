package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData

class ActorSeriesAdapter(private val context: Context) :
    RecyclerView.Adapter<ActorSeriesViewHolder>() {

    private val requestManager = Glide.with(context)
    private val seriesList = mutableListOf<ActorSeriesCastData>()

    var seriesClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorSeriesViewHolder =
        ActorSeriesViewHolder(R.layout.item_actor_series, parent)

    override fun onBindViewHolder(holder: ActorSeriesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.castClickListener = seriesClickListener
            holder.requestManager = requestManager
            holder.bind(it)
        }
    }

    override fun getItemCount() = seriesList.size

    override fun onViewRecycled(holder: ActorSeriesViewHolder) {
        super.onViewRecycled(holder)
        holder.recycleView()
    }

    fun submitList(series: List<ActorSeriesCastData>) {
        seriesList.addAll(series)
        notifyDataSetChanged()
    }

    fun clearList() {
        seriesList.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ActorSeriesCastData? {
        return if (position < seriesList.size) seriesList[position]
        else null
    }
}