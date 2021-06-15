package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movie.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.MovieData
import utopia.ikbal.simplemovieapplication.extensions.*
import utopia.ikbal.simplemovieapplication.ui.MovieDetailsActivity
import utopia.ikbal.simplemovieapplication.ui.adapter.MovieAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment() {

    private lateinit var searchMovieViewModel: SearchMovieViewModel
    private lateinit var adapter: MovieAdapter

    private val filteredMovieListObserver = Observer<NetworkResult<List<MovieData>?>> { it ->
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> adapter.submitList(it1) } },
            noData = {
                showNoResultsFound(true)
            },
            onError = { showGenericError("Something is wrong") }
            /*{ it1 -> it1.message?.let { it1 -> showGenericError(it1) } }*/
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_search_movie, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObserver()
        initRecyclerView()
        initSearchView()
        initOnBackPressedDispatcher()
        if (savedInstanceState != null) {
            val oldQuery = savedInstanceState.get(QUERY)
            searchMovieViewModel.loadInitial(oldQuery.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY, searchView.query.toString())
    }

    private fun showLoading() {
        progress_bar_search.visible()
    }

    private fun hideLoading() {
        progress_bar_search.gone()
    }

    private fun showNoResultsFound(issue: Boolean) {
        if (issue) {
            text_view_no_results_found.text = getString(R.string.no_results_for_such_criteria)
        }
        no_results_linear_layout.visible()
    }

    private fun hideNoResultsFound() {
        no_results_linear_layout.gone()
    }

    private fun onBackPressed() {
        if (!progress_bar_search.isVisible()) {
            this.navigateUp()
        }
    }

    private fun initOnBackPressedDispatcher() {
        this.addOnBackPressedDispatcher { onBackPressed() }
    }

    private fun initSearchView() {
        showNoResultsFound(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clearList()
                query?.let { searchMovieViewModel.loadInitial(it) }
                searchView.clearFocus()
                hideNoResultsFound()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(requireContext())
        adapter.movieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(movieId: Int) {
                MovieDetailsActivity.launch(requireContext(), movieId)
            }
        }
        recycler_view_search_movie.addOnScrollListener(object :
            PaginationScrollListener(recycler_view_search_movie.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                searchMovieViewModel.loadAfter()
            }

            override val isLastPage: Boolean
                get() = searchMovieViewModel.isLastPage
            override val isLoading: Boolean
                get() = searchMovieViewModel.loading
        })
        recycler_view_search_movie.adapter = adapter
    }

    private fun initViewModel() {
        searchMovieViewModel = ViewModelProvider(this).get(SearchMovieViewModel::class.java)
    }

    private fun initObserver() {
        with(searchMovieViewModel) {
            movieListLiveData.observe(viewLifecycleOwner, filteredMovieListObserver)
        }
    }

    companion object {
        private const val QUERY = "query"
    }
}