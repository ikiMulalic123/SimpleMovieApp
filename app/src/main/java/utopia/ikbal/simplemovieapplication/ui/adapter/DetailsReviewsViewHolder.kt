package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.ReviewData
import utopia.ikbal.simplemovieapplication.data.model.ReviewRatingData
import utopia.ikbal.simplemovieapplication.extensions.inflate

class DetailsReviewsViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val tvReviewName: TextView = itemView.findViewById(R.id.tv_details_review_title)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar_details_reviews)
    private val tvReviewInfo: TextView = itemView.findViewById(R.id.tv_details_review_info)

    fun bind(reviewData: ReviewData) {
        tvReviewName.text = reviewData.author
        ratingBar.rating = reviewData.author_details.rating /2
        tvReviewInfo.text = reviewData.content
    }
}