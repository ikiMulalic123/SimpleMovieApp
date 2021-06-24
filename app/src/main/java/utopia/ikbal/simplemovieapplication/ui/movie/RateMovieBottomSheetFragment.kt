package utopia.ikbal.simplemovieapplication.ui.movie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_rate_movie.*
import utopia.ikbal.simplemovieapplication.R
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.MY_SHARED_PREFERENCE
import utopia.ikbal.simplemovieapplication.data.model.RateMovieData
import utopia.ikbal.simplemovieapplication.data.model.RateMovieResponseData
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import utopia.ikbal.simplemovieapplication.util.NetworkResultProcessor

@AndroidEntryPoint
class RateMovieBottomSheetFragment : BottomSheetDialogFragment(), NetworkResultProcessor {

    private lateinit var rateMovieViewModel: RateMovieViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private val rateMovieObserver = Observer<NetworkResult<RateMovieResponseData>?> {
        processNetworkResult(
            it,
            data = { data -> rateMovieSuccession(data) },
            onError = { showGenericError("Something went wrong") }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_rate_movie, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initSharedPreference()
        rateMovie()
        initObserver()
    }

    override fun showGenericError(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    private fun initSharedPreference() {
        sharedPreferences = requireContext()
            .getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    private fun rateMovieSuccession(rateMovieResponseData: RateMovieResponseData) {
        if (rateMovieResponseData.status_code == 1) {
            Toast.makeText(requireContext(), "You successfully rated movie", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }

    private fun rateMovie() {

    }

    private fun initViewModel() {
        rateMovieViewModel = ViewModelProvider(this).get(RateMovieViewModel::class.java)
    }

    private fun initObserver() {
        with(rateMovieViewModel) {
            rateMovieLiveData.observe(viewLifecycleOwner, rateMovieObserver)
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"

        private fun newInstance(movieId: Int) = RateMovieBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID, movieId)
            }
        }

        fun show(fragmentManager: FragmentManager, movieId: Int) {
            newInstance(movieId).show(fragmentManager, RateMovieBottomSheetFragment::javaClass.name)
        }
    }
}