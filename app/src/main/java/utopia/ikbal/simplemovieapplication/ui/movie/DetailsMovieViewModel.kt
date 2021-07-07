package utopia.ikbal.simplemovieapplication.ui.movie

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.ui.MovieDetailsActivity
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel
@Inject
constructor(private val detailsMovieRepository: DetailsMovieRepository) : BaseViewModel() {

    lateinit var movieId: Integer
    private val _detailsLiveData = MutableLiveData<NetworkResult<DetailsData>>()
    private val _castLiveData = MutableLiveData<NetworkResult<List<CastData>>>()
    private val _reviewsLiveData = MutableLiveData<NetworkResult<List<ReviewData>>>()
    private val _videosLiveData = MutableLiveData<NetworkResult<VideoData?>>()
    private val _imagesLiveData = MutableLiveData<NetworkResult<ImageData?>>()

    private var page: Int = 1
    private var totalPages: Int? = 1

    val videosLiveData: LiveData<NetworkResult<VideoData?>> = _videosLiveData
    val imagesLiveData: LiveData<NetworkResult<ImageData?>> = _imagesLiveData
    val detailsLiveData: LiveData<NetworkResult<DetailsData>> = _detailsLiveData
    val castLiveData: LiveData<NetworkResult<List<CastData>>> = _castLiveData
    val reviewsLiveData: LiveData<NetworkResult<List<ReviewData>>> = _reviewsLiveData

    var isLastPage: Boolean = false

    fun getDetails() {
        addToDisposable(detailsMovieRepository.getDetails(movieId.toInt())
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _detailsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _detailsLiveData.value = NetworkResult.Data(it)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getCasts() {
        addToDisposable(detailsMovieRepository.getCredits(movieId.toInt())
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _castLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _castLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _castLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getReviews() {
        page = 1
        addToDisposable(detailsMovieRepository.getReviews(movieId.toInt(), page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _reviewsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                totalPages = it.totalPages
                _reviewsLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let { page <= it } == true)
                if (isLastPage) page++
                getReviewsAfter()
            }, {
                _reviewsLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getReviewsAfter() {
        addToDisposable(detailsMovieRepository.getReviews(movieId.toInt(), page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _reviewsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _reviewsLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let { page < it } == true)
                if (isLastPage) page++
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getVideos() {
        addToDisposable(detailsMovieRepository.getVideos(movieId.toInt())
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _videosLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _videosLiveData.value = NetworkResult.Data(it.results.firstOrNull())
            }, {
                _videosLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getImages() {
        addToDisposable(detailsMovieRepository.getImages(movieId.toInt())
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _imagesLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _imagesLiveData.value = NetworkResult.Data(it.posters.lastOrNull())
            }, {
                _imagesLiveData.value = NetworkResult.Error(it)
            })
        )
    }
}