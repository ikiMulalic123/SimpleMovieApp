package utopia.ikbal.simplemovieapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_actor.*
import kotlinx.android.synthetic.main.toolbar_actor_activity.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorImageData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData
import utopia.ikbal.simplemovieapplication.extensions.load
import utopia.ikbal.simplemovieapplication.ui.adapter.ActorMovieAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.ActorSeriesAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseActivity
import utopia.ikbal.simplemovieapplication.ui.movie.ActorMovieViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import java.util.*

@AndroidEntryPoint
class MovieActorActivity : BaseActivity() {

    private lateinit var actorMovieViewModel: ActorMovieViewModel
    private lateinit var requestManager: RequestManager
    private lateinit var actorMovieAdapter: ActorMovieAdapter
    private lateinit var actorSeriesAdapter: ActorSeriesAdapter

    private val actorDetailsObserver = Observer<NetworkResult<ActorData>?> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { data -> submitDetails(data) },
            { showGenericError("Something went wrong") }
        )
    }

    private val actorMoviesObserver = Observer<NetworkResult<List<ActorMovieAsActorData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> actorMovieAdapter.submitList(it1) } },
            { showGenericError("Something went wrong") }
        )
    }

    private val actorSeriesObserver = Observer<NetworkResult<List<ActorSeriesCastData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> actorSeriesAdapter.submitList(it1) } },
            { showGenericError("Something went wrong") }
        )
    }

    private val imageObserver = Observer<NetworkResult<List<ActorImageData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { data -> data?.let { it1 -> initImages(it1) } },
            { showGenericError("Something went wrong") }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_actor)
        initViewModel()
        initObserver()
        initMovieRecyclerView()
        initSeriesRecyclerView()
        //initClickListener()
    }

    private fun initClickListener() {
        img_actor_back_button.setOnClickListener{
            //TODO
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun submitDetails(data: ActorData) {
        img_actor.load(Constants.BASE_IMAGE_URL + data.profile_path, requestManager)
        tv_actor_title.text = data.name
        tv_known_for_actor.text = data.known_for_department
        tv_actor_info.text = data.biography
    }

    private fun initImages (data: List<ActorImageData>){
        img_cast_poster.load(Constants.BASE_IMAGE_URL + data[1].file_path, requestManager)
    }

    private fun initSeriesRecyclerView() {
        actorSeriesAdapter = ActorSeriesAdapter(this)
        actorSeriesAdapter.seriesClickListener = object : OnMovieClickListener {
            override fun onMovieClick(castId: Int) {
                MovieDetailsActivity.launch(this@MovieActorActivity, castId)
            }
        }
        rv_actor_tv_series.adapter = actorSeriesAdapter
    }

    private fun initMovieRecyclerView() {
        actorMovieAdapter = ActorMovieAdapter(this)
        actorMovieAdapter.movieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(castId: Int) {
                MovieDetailsActivity.launch(this@MovieActorActivity, castId)
            }
        }
        rv_actor_movies.adapter = actorMovieAdapter
    }

    private fun initObserver() {
        with(actorMovieViewModel) {
            actorLiveData.observe(this@MovieActorActivity, actorDetailsObserver)
            actorMovieAsActorLiveData.observe(this@MovieActorActivity, actorMoviesObserver)
            actorSeriesAsActorLiveData.observe(this@MovieActorActivity, actorSeriesObserver)
            actorImagesLiveData.observe(this@MovieActorActivity, imageObserver)
        }
    }

    private fun initViewModel() {
        val castId = intent.getIntExtra(CAST_ID, -1)
        if (castId == -1) finish()
        requestManager = Glide.with(this)
        actorMovieViewModel = ViewModelProvider(this).get(ActorMovieViewModel::class.java)
        actorMovieViewModel.getDetails(castId)
        actorMovieViewModel.getMoviesAsActor(castId)
        actorMovieViewModel.getActorSeries(castId)
        actorMovieViewModel.getImages(castId)
    }

    companion object {
        const val CAST_ID = "cast_id"

        private fun createLaunchIntent(context: Context, castId: Int) =
            Intent(context, MovieActorActivity::class.java).apply {
                putExtra(CAST_ID, castId)
            }

        fun launch(context: Context, castId: Int) =
            context.startActivity(createLaunchIntent(context, castId))
    }
}