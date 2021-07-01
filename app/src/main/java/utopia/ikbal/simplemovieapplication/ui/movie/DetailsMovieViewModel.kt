package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.*
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.DetailsMovieRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel
@Inject
constructor(private val detailsMovieRepository: DetailsMovieRepository) : BaseViewModel() {

    private val _detailsLiveData = MutableLiveData<NetworkResult<DetailsData>?>()
    private val _castLiveData = MutableLiveData<NetworkResult<List<CastData>?>>()
    private val _reviewsLiveData = MutableLiveData<NetworkResult<List<ReviewData>?>>()
    private val _videosLiveData = MutableLiveData<NetworkResult<List<VideoData>?>>()
    private val _imagesLiveData = MutableLiveData<NetworkResult<List<ImageData>?>>()

    private var page: Int = 1
    private var totalPages: Int? = 1

    val videosLiveData: LiveData<NetworkResult<List<VideoData>?>> = _videosLiveData
    val imagesLiveData: LiveData<NetworkResult<List<ImageData>?>> = _imagesLiveData
    val detailsLiveData: LiveData<NetworkResult<DetailsData>?> = _detailsLiveData
    val castLiveData: LiveData<NetworkResult<List<CastData>?>> = _castLiveData
    val reviewsLiveData: LiveData<NetworkResult<List<ReviewData>?>> = _reviewsLiveData

    var loading: Boolean = false
    var isLastPage: Boolean = false

    fun getDetails(id: Int) {
        addToDisposable(detailsMovieRepository.getDetails(id)
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

    fun getCasts(id: Int) {
        addToDisposable(detailsMovieRepository.getCredits(id)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _castLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _castLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getReviews(id: Int) {
        page = 1
        addToDisposable(detailsMovieRepository.getReviews(id, page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _reviewsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                totalPages = it.total_pages
                _reviewsLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let { page <= it } == true)
                if (isLastPage) page++
                getReviewsAfter(id)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun getReviewsAfter(id: Int) {
        addToDisposable(detailsMovieRepository.getReviews(id, page)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _reviewsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _reviewsLiveData.value = NetworkResult.Data(it.results)
                isLastPage = (totalPages?.let { page < it } == true)
                if (isLastPage) page++
                loading = false
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun getVideos(id: Int) {
        addToDisposable(detailsMovieRepository.getVideos(id)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _videosLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _videosLiveData.value = NetworkResult.Data(it.results)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getImages(id: Int) {
        addToDisposable(detailsMovieRepository.getImages(id)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _imagesLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _imagesLiveData.value = NetworkResult.Data(it.posters)
            }, {
                _imagesLiveData.value = NetworkResult.Error(it)
            })
        )
    }
}