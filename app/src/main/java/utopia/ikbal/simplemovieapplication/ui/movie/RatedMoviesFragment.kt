package utopia.ikbal.simplemovieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rated_movies.*
import kotlinx.android.synthetic.main.toolbar_details_activity.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.model.RatedMovieData
import utopia.ikbal.simplemovieapplication.extensions.*
import utopia.ikbal.simplemovieapplication.ui.adapter.RatedMovieAdapter
import utopia.ikbal.simplemovieapplication.ui.base.BaseFragment
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.PaginationScrollListener

class RatedMoviesFragment : BaseFragment() {

    private lateinit var ratedMovieViewModel: RatedMovieViewModel
    private lateinit var adapter: RatedMovieAdapter

    private val ratedMovieObserver = Observer<NetworkResult<List<RatedMovieData>?>> {
        processNetworkResult(
            it,
            ::showLoading,
            ::hideLoading,
            { list -> list?.let { data -> adapter.submitList(data) } },
            { showGenericError("Something went wrong") }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_rated_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnBackPressedDispatcher()
        initViewModel()
        initRecyclerView()
        initObserver()
    }

    private fun onBackPressed() {
        if (!linear_layout_rated_movies.isVisible()) {
            this.navigateUp()
        }
    }

    private fun initOnBackPressedDispatcher() {
        this.addOnBackPressedDispatcher { onBackPressed() }
        img_details_back_button.setOnClickListener {
            navigateUp()
        }
    }

    private fun showLoading() {
        linear_layout_rated_movies.visible()
    }

    private fun hideLoading() {
        linear_layout_rated_movies.gone()
    }

    private fun initRecyclerView() {
        adapter = RatedMovieAdapter(requireContext())
        rv_rated_movies.addOnScrollListener(object :
            PaginationScrollListener(rv_rated_movies.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                ratedMovieViewModel.loadAfter()
            }

            override val isLastPage: Boolean
                get() = ratedMovieViewModel.isLastPage
            override val isLoading: Boolean
                get() = ratedMovieViewModel.loading
        })
        rv_rated_movies.adapter = adapter
    }

    private fun initViewModel() {
        ratedMovieViewModel =
            ViewModelProvider(requireActivity()).get(RatedMovieViewModel::class.java)
        with(ratedMovieViewModel) {
            getRatedMovies()
        }
    }

    private fun initObserver() {
        with(ratedMovieViewModel) {
            ratedMovieListLiveData.observe(viewLifecycleOwner, ratedMovieObserver)
        }
    }
}