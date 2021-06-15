package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.CastData
import utopia.ikbal.simplemovieapplication.data.model.DetailsData
import utopia.ikbal.simplemovieapplication.data.model.ReviewData
import utopia.ikbal.simplemovieapplication.data.model.VideoData
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

    private var page: Int = 1
    private var totalPages: Int? = 1

    val videosLiveData: LiveData<NetworkResult<List<VideoData>?>> = _videosLiveData
    val detailsLiveData: LiveData<NetworkResult<DetailsData>?> = _detailsLiveData
    val castLiveData: LiveData<NetworkResult<List<CastData>?>> = _castLiveData
    val reviewsLiveData: LiveData<NetworkResult<List<ReviewData>?>> = _reviewsLiveData

    var loading: Boolean = false
    var isLastPage: Boolean = false

    fun getDetails(id: Int) {
        addToDisposable(detailsMovieRepository.getDetails(id)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _detailsLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _detailsLiveData.value = NetworkResult.Data(it)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun getCasts(id: Int) {
        addToDisposable(detailsMovieRepository.getCredits(id)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _castLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _castLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
                loading = false
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
                isLastPage = (page == totalPages)
                page++
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
                isLastPage = (page == totalPages)
                page++
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
                loading = true
                _videosLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _videosLiveData.value = NetworkResult.Data(it.results)
            }, {
                _detailsLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }
}