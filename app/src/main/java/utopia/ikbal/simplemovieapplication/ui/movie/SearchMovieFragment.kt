package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_movie.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.MovieData
import utopia.ikbal.simplemovieapplication.extensions.*
import utopia.ikbal.simplemovieapplication.ui.adapter.MovieAdapter
import utopia.ikbal.simplemovieapplication.ui.adapter.OnMovieClickListener
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener

class SearchMovieFragment : BaseFragment() {

    private lateinit var searchMovieViewModel: SearchMovieViewModel
    private lateinit var adapter: MovieAdapter

    private val filteredMovieListObserver = Observer<NetworkResult<List<MovieData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { it1 -> adapter.submitList(it1) } },
            noData = {
                Toast.makeText(requireContext(), " alksdja", Toast.LENGTH_SHORT).show()
            },
            { showGenericError("Error") }
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
    }

    private fun showLoading() {
        progress_bar_search.visible()
    }

    private fun hideLoading() {
        progress_bar_search.gone()
    }

    private fun initOnBackPressedDispatcher() {
        this.addOnBackPressedDispatcher { onBackPressed() }
    }

    private fun onBackPressed() {
        if (!progress_bar_search.isVisible()) {
            this.navigateUp()
        }
    }

    private fun initSearchView() {
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clearList()
                query?.let { searchMovieViewModel.loadInitial(it) }
                searchView.clearFocus()
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
                Toast.makeText(requireContext(), "You clicked on $movieId item", Toast.LENGTH_SHORT)
                    .show()
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
}