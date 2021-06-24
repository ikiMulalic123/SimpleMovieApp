package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.ReviewData

class DetailsReviewsAdapter : RecyclerView.Adapter<DetailsReviewsViewHolder>() {

    private val reviewsList = mutableListOf<ReviewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsReviewsViewHolder =
        DetailsReviewsViewHolder(R.layout.item_details_reviews, parent)

    override fun onBindViewHolder(holder: DetailsReviewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = reviewsList.size

    fun submitList(reviews: List<ReviewData>) {
        reviewsList.addAll(reviews)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ReviewData? {
        return if (position < reviewsList.size) reviewsList[position]
        else null
    }
}