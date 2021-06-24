package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.MovieFragmentType
import utopia.ikbal.simplemovieapplication.data.model.MovieData
import utopia.ikbal.simplemovieapplication.extensions.addOnBackPressedDispatcher
import utopia.ikbal.simplemovieapplication.extensions.gone
import utopia.ikbal.simplemovieapplication.extensions.isVisible
import utopia.ikbal.simplemovieapplication.extensions.visible
import utopia.ikbal.simplemovieapplication.ui.MovieDetailsActivity
import utopia.ikbal.simplemovieapplication.ui.adapter.MovieAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener

@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    private val movieListObserver = Observer<NetworkResult<List<MovieData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> adapter.submitList(it1) } },
            { showGenericError("Something went wrong") })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_movie, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefresh()
        initOnBackPressedDispatcher()
        initViewModel()
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clearList()
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            updateDataOnRefresh()
        }
    }

    private fun updateDataOnRefresh() {
        adapter.clearList()
        movieViewModel.loadInitial(getMovieType())
    }

    private fun showLoading() {
        linear_layout.visible()
    }

    private fun hideLoading() {
        linear_layout.gone()
        swipe_refresh.isRefreshing = false
    }

    private fun initOnBackPressedDispatcher() {
        this.addOnBackPressedDispatcher { onBackPressed() }
    }

    private fun onBackPressed() {
        if (!linear_layout.isVisible()) {
            requireActivity().finish()
        }
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(requireContext())
        adapter.movieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(movieId: Int) {
                MovieDetailsActivity.launch(requireContext(), movieId)
            }
        }
        recycler_view_fragment.addOnScrollListener(object :
            PaginationScrollListener(recycler_view_fragment.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                movieViewModel.loadAfter(getMovieType())
            }

            override val isLastPage: Boolean
                get() = movieViewModel.isLastPage
            override val isLoading: Boolean
                get() = movieViewModel.loading
        })
        recycler_view_fragment.adapter = adapter
    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.loadInitial(getMovieType())
    }

    private fun initObservers() {
        with(movieViewModel) {
            movieListLiveData.observe(viewLifecycleOwner, movieListObserver)
        }
    }

    private fun getMovieType(): MovieFragmentType {
        return when (arguments?.get(EXTRA_MOVIE_TYPE)) {
            MovieFragmentType.LATEST.fragmentResNameId -> MovieFragmentType.LATEST
            MovieFragmentType.RECENT.fragmentResNameId -> MovieFragmentType.RECENT
            MovieFragmentType.POPULAR.fragmentResNameId -> MovieFragmentType.POPULAR
            else -> MovieFragmentType.LATEST
        }
    }

    companion object {
        private const val EXTRA_MOVIE_TYPE = "extra_movie_type"

        fun newInstance(movieType: Int) = MovieFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_MOVIE_TYPE, movieType)
            }
        }
    }
}