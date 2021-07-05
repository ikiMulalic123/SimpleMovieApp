package utopia.ikbal.simplemovieapplication.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.toolbar_details_activity.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.BASE_YOUTUBE_URL
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.extensions.loadImageWithPlaceholder
import utopia.ikbal.simplemovieapplication.extensions.visible
import utopia.ikbal.simplemovieapplication.ui.adapter.DetailsCastAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.DetailsReviewsAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.DetailsMovieViewModel
import utopia.ikbal.simplemovieapplication.ui.movie.RateMovieBottomSheetFragment
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity() {

    private var movieId: Int = 0
    private lateinit var detailsMovieViewModel: DetailsMovieViewModel
    private val requestManager: RequestManager by lazy { Glide.with(this) }
    private lateinit var adapterCast: DetailsCastAdapter
    private lateinit var adapterReview: DetailsReviewsAdapter

    private val detailsObserver = Observer<NetworkResult<DetailsData>?> {
        processNetworkResult(
            it,
            data = { data -> submitDetailsData(data) },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val videosObserver = Observer<NetworkResult<List<VideoData>?>> {
        processNetworkResult(
            it,
            data = { list -> list?.let { data -> initializeVideo(data) } },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val imagesObserver = Observer<NetworkResult<List<ImageData>?>> {
        processNetworkResult(
            it,
            data = { list -> initImage(list) },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val castsObserver = Observer<NetworkResult<List<CastData>?>> {
        processNetworkResult(
            it,
            data = { list -> list?.let { data -> adapterCast.submitList(data) } },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val reviewsObserver = Observer<NetworkResult<List<ReviewData>?>> {
        processNetworkResult(
            it,
            data = { list -> list?.let { data -> adapterReview.submitList(data) } },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initMovieId()
        initViewModel()
        initCastRecyclerView()
        initReviewRecyclerView()
        initObserver()
        initOnBackPressed()
        initRating()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        movieId = intent.getIntExtra(MOVIE_ID, INVALID_ID)
        if (movieId == -1) finish()
        initViewModel()
    }

    private fun initMovieId() {
        movieId = intent.getIntExtra(MOVIE_ID, INVALID_ID)
        if (movieId == -1) finish()
        detailsMovieViewModel = ViewModelProvider(this).get(DetailsMovieViewModel::class.java)
    }

    private fun initOnBackPressed() {
        img_details_back_button.setOnClickListener { finish() }
    }

    private fun initImage(data: List<ImageData>?) {
        val imageData = data?.get(0)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels
        val ratio = width / height.toFloat()
        val newHeight = (ratio * (imageData?.height ?: 1)).roundToInt()
        img_placeholder.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, newHeight)
        (img_placeholder.layoutParams as ConstraintLayout.LayoutParams).topToBottom =
            R.id.toolbar_details_movie
        img_details_poster.loadImageWithPlaceholder(Constants.BASE_ORIGINAL_IMAGE_URL + imageData?.filePath,
            requestManager)
    }

    private fun initializeVideo(data: List<VideoData>?) {
        if (data?.isEmpty() == true) return
        else linear_layout_img_play.visible()
        val ytIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_url) + data?.get(0)?.key))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL + data?.get(0)?.key))

        btn_details_play.setOnClickListener {
            try {
                startActivity(ytIntent)
            } catch (exc: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }
    }

    private fun initRating() {
        tv_rate_movie.setOnClickListener {
            RateMovieBottomSheetFragment.show(supportFragmentManager, movieId)
        }
    }

    private fun submitDetailsData(detailsData: DetailsData) {
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
        with(detailsMovieViewModel) {
            getDetails(movieId)
            getVideos(movieId)
            getImages(movieId)
            getCasts(movieId)
            getReviews(movieId)
        }
    }

    private fun initObserver() {
        with(detailsMovieViewModel) {
            detailsLiveData.observe(this@MovieDetailsActivity, detailsObserver)
            videosLiveData.observe(this@MovieDetailsActivity, videosObserver)
            imagesLiveData.observe(this@MovieDetailsActivity, imagesObserver)
            castLiveData.observe(this@MovieDetailsActivity, castsObserver)
            reviewsLiveData.observe(this@MovieDetailsActivity, reviewsObserver)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        private const val INVALID_ID = -1

        private fun createLaunchIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }

        fun launch(context: Context, movieId: Int) =
            context.startActivity(createLaunchIntent(context, movieId))
    }
}