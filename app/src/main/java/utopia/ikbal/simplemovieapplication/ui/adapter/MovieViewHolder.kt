package utopia.ikbal.simplemovieapplication.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.BASE_IMAGE_URL
import utopia.ikbal.simplemovieapplication.data.model.MovieData
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieData
import utopia.ikbal.simplemovieapplication.extensions.checkIfIsNull
import utopia.ikbal.simplemovieapplication.extensions.inflate
import utopia.ikbal.simplemovieapplication.extensions.load

class MovieViewHolder(@LayoutRes layout: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(parent.inflate(layout, false)) {

    private val image: ImageView = itemView.findViewById(R.id.img_movie_poster)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar_movie)
    private val ratingBarMarkTV: TextView = itemView.findViewById(R.id.tv_rating)
    private val movieTitleTV: TextView = itemView.findViewById(R.id.tv_title)
    private val movieInfoTV: TextView = itemView.findViewById(R.id.tv_info)
    var requestManager: RequestManager? = null
    var movieClickListener: OnMovieClickListener? = null

    fun bind(movieData: MovieData) {
        itemView.setOnClickListener {
            movieClickListener?.onMovieClick(movieData.id)
        }
        movieTitleTV.text = movieData.originalTitle
        movieInfoTV.text = movieData.overview
        ratingBarMarkTV.text = (movieData.voteAverage / 2).toString()
        ratingBar.rating = movieData.voteAverage / 2
        image.load(BASE_IMAGE_URL + movieData.posterPath, requestManager)
    }

    fun bindRatedMovies(ratedMovieData: RatedMovieData) {
        movieTitleTV.text = ratedMovieData.originalTitle
        movieInfoTV.text = ratedMovieData.overview
        ratingBarMarkTV.text = (ratedMovieData.rating / 2).toString()
        ratingBar.rating = ratedMovieData.rating / 2
        image.load(BASE_IMAGE_URL + ratedMovieData.posterPath, requestManager)
    }

    fun recycleView() {
        if (itemView.context.checkIfIsNull()) requestManager?.clear(image)
    }
}