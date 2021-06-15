package utopia.ikbal.simplemovieapplication.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_movie.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.BASE_YOUTUBE_URL
import utopia.ikbal.simplemovieapplication.data.model.CastData
import utopia.ikbal.simplemovieapplication.data.model.DetailsData
import utopia.ikbal.simplemovieapplication.data.model.ReviewData
import utopia.ikbal.simplemovieapplication.data.model.VideoData
import utopia.ikbal.simplemovieapplication.extensions.load
import utopia.ikbal.simplemovieapplication.extensions.visible
import utopia.ikbal.simplemovieapplication.ui.adapter.DetailsCastAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.DetailsReviewsAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.DetailsMovieViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity() {

    private lateinit var detailsMovieViewModel: DetailsMovieViewModel
    private lateinit var requestManager: RequestManager
    private lateinit var adapterCast: DetailsCastAdapter
    private lateinit var adapterReview: DetailsReviewsAdapter

    private val detailsObserver = Observer<NetworkResult<DetailsData>?> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { it1 -> it1.let { it2 -> submitDetailsData(it2) } },
            { showGenericError("Something went wrong") }
        )
    }

    private val videosObserver = Observer<NetworkResult<List<VideoData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> initializeVideo(it1) } },
            { showGenericError("something went wrong") }
        )
    }

    private val castsObserver = Observer<NetworkResult<List<CastData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> adapterCast.submitList(it1) } },
            { showGenericError("Something went wrong") }
        )
    }

    private val reviewsObserver = Observer<NetworkResult<List<ReviewData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> adapterReview.submitList(it1) } },
            { showGenericError("Something went wrong") }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViewModel()
        initCastRecyclerView()
        initReviewRecyclerView()
        initObserver()
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun initializeVideo(data1: List<VideoData>?) {
        if (data1?.isEmpty() == true) {
            return
        } else {
            ll_img_play.visible()
        }
        val ytIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + data1?.get(0)?.key))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL + data1?.get(0)?.key))

        btn_details_play.setOnClickListener {
            try {
                startActivity(ytIntent)
            } catch (exc: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }
    }

    private fun submitDetailsData(detailsData: DetailsData) {
        img_details_poster.load(Constants.BASE_IMAGE_URL + detailsData.backdrop_path,
            requestManager)
        tv_details_title.text = detailsData.original_title
        rating_bar_details.rating = detailsData.vote_average / 2
        tv_rating_details.text = (detailsData.vote_average / 2).toString()
        tv_info_details.text = detailsData.overview
    }

    private fun initCastRecyclerView() {
        adapterCast = DetailsCastAdapter(this)
        adapterCast.castClickListener = object : OnMovieClickListener {
            override fun onMovieClick(castId: Int) {
                MovieActorActivity.launch(this@MovieDetailsActivity, castId)
            }
        }
        rv_details_cast.adapter = adapterCast
    }

    private fun initReviewRecyclerView() {
        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        if (movieId == -1) finish()
        adapterReview = DetailsReviewsAdapter()
        rv_details_reviews.addOnScrollListener(object :
            PaginationScrollListener(rv_details_reviews.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                detailsMovieViewModel.getReviewsAfter(movieId)
            }

            override val isLastPage: Boolean
                get() = detailsMovieViewModel.isLastPage
            override val isLoading: Boolean
                get() = detailsMovieViewModel.loading
        })
        rv_details_reviews.adapter = adapterReview
    }

    private fun initViewModel() {
        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        if (movieId == -1) finish()
        requestManager = Glide.with(this)
        detailsMovieViewModel = ViewModelProvider(this).get(DetailsMovieViewModel::class.java)
        detailsMovieViewModel.getDetails(movieId)
        detailsMovieViewModel.getVideos(movieId)
        detailsMovieViewModel.getCasts(movieId)
        detailsMovieViewModel.getReviews(movieId)
    }

    private fun initObserver() {
        with(detailsMovieViewModel) {
            detailsLiveData.observe(this@MovieDetailsActivity, detailsObserver)
            videosLiveData.observe(this@MovieDetailsActivity, videosObserver)
            castLiveData.observe(this@MovieDetailsActivity, castsObserver)
            reviewsLiveData.observe(this@MovieDetailsActivity, reviewsObserver)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"

        private fun createLaunchIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }

        fun launch(context: Context, movieId: Int) =
            context.startActivity(createLaunchIntent(context, movieId))
    }
}