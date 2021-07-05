package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_movie_actor.*
import kotlinx.android.synthetic.main.toolbar_actor_activity.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieData
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData
import utopia.ikbal.simplemovieapplication.data.model.ImageData
import utopia.ikbal.simplemovieapplication.extensions.load
import utopia.ikbal.simplemovieapplication.extensions.loadImageWithPlaceholder
import utopia.ikbal.simplemovieapplication.ui.adapter.ActorMovieAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.ActorSeriesAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.ActorMovieViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieActorActivity : BaseActivity(), View.OnClickListener {

    private lateinit var actorMovieViewModel: ActorMovieViewModel
    private lateinit var requestManager: RequestManager
    private lateinit var actorMovieAdapter: ActorMovieAdapter
    private lateinit var actorSeriesAdapter: ActorSeriesAdapter

    private val actorDetailsObserver = Observer<NetworkResult<ActorData>?> {
        processNetworkResult(
            it,
            data = { data -> submitDetails(data) },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val actorMoviesObserver = Observer<NetworkResult<List<ActorMovieData>?>> {
        processNetworkResult(
            it,
            data = { list -> list?.let { data -> actorMovieAdapter.submitList(data) } },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val actorSeriesObserver = Observer<NetworkResult<List<ActorSeriesCastData>?>> {
        processNetworkResult(
            it,
            data = { list -> list?.let { data -> actorSeriesAdapter.submitList(data) } },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    private val imageObserver = Observer<NetworkResult<List<ImageData>?>> {
        processNetworkResult(
            it,
            data = { data -> initImages(data) },
            onError = { showGenericError(getString(R.string.something_went_wrong)) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_actor)
        initViewModel()
        initObserver()
        initMovieRecyclerView()
        initSeriesRecyclerView()
        initClickListener()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_actor_back_button -> finish()
        }
    }

    private fun initClickListener() {
        img_actor_back_button.setOnClickListener(this)
    }

    private fun submitDetails(data: ActorData) {
        img_actor.load(Constants.BASE_IMAGE_URL + data.profile_path, requestManager)
        tv_actor_title.text = data.name
        tv_known_for_actor.text = data.known_for_department
        tv_actor_info.text = data.biography
    }

    private fun initImages(data: List<ImageData>?) {
        val imageData = data?.get(0)
        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels
        val ratio = width / height.toFloat()
        val newHeight = (ratio * (imageData?.height ?: 1)).roundToInt()
        img_placeholder_actor.layoutParams =
            ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, newHeight)
        (img_placeholder_actor.layoutParams as ConstraintLayout.LayoutParams).topToBottom =
            R.id.toolbar_actor_movie
        img_cast_poster.loadImageWithPlaceholder(Constants.BASE_ORIGINAL_IMAGE_URL + imageData?.filePath,
            requestManager)
    }

    private fun initSeriesRecyclerView() {
        actorSeriesAdapter = ActorSeriesAdapter(this)
        rv_actor_tv_series.adapter = actorSeriesAdapter
    }

    private fun initMovieRecyclerView() {
        actorMovieAdapter = ActorMovieAdapter(this)
        actorMovieAdapter.movieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(movieId: Int) {
                finish()
                MovieDetailsActivity.launch(this@MovieActorActivity, movieId)
            }
        }
        rv_actor_movies.adapter = actorMovieAdapter
    }

    private fun initObserver() {
        with(actorMovieViewModel) {
            actorLiveData.observe(this@MovieActorActivity, actorDetailsObserver)
            actorMovieLiveData.observe(this@MovieActorActivity, actorMoviesObserver)
            actorSeriesLiveData.observe(this@MovieActorActivity, actorSeriesObserver)
            actorImagesLiveData.observe(this@MovieActorActivity, imageObserver)
        }
    }

    private fun initViewModel() {
        val castId = intent.getIntExtra(CAST_ID, INVALID_ID)
        if (castId == -1) finish()
        requestManager = Glide.with(this)
        actorMovieViewModel = ViewModelProvider(this).get(ActorMovieViewModel::class.java)
        with(actorMovieViewModel) {
            getDetails(castId)
            getMoviesAsActor(castId)
            getActorSeries(castId)
            getImages(castId)
        }
    }

    companion object {
        const val CAST_ID = "cast_id"
        private const val INVALID_ID = -1

        private fun createLaunchIntent(context: Context, castId: Int) =
            Intent(context, MovieActorActivity::class.java).apply {
                putExtra(CAST_ID, castId)
            }

        fun launch(context: Context, castId: Int) =
            context.startActivity(createLaunchIntent(context, castId))
    }
}