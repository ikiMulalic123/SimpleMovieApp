package utopia.ikbal.simplemovieapplication.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.CastData

class DetailsCastAdapter(private val context: Context) :
    RecyclerView.Adapter<DetailsCastViewHolder>() {

    private val requestManager = Glide.with(context)
    private val castList = mutableListOf<CastData>()

    var castClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCastViewHolder =
        DetailsCastViewHolder(R.layout.item_details_cast, parent)

    override fun onBindViewHolder(holder: DetailsCastViewHolder, position: Int) {
        getItem(position)?.let {
            holder.castClickListener = castClickListener
            holder.requestManager = requestManager
            holder.bind(it)
        }
    }

    override fun getItemCount() = castList.size

    override fun onViewRecycled(holder: DetailsCastViewHolder) {
        super.onViewRecycled(holder)
        holder.recycleView()
    }

    fun submitList(casts: List<CastData>) {
        castList.addAll(casts)
        notifyDataSetChanged()
    }

    fun clearList() {
        castList.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): CastData? {
        return if (position < castList.size) castList[position]
        else null
    }
}