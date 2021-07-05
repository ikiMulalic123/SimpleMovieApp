package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieData
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData
import utopia.ikbal.simplemovieapplication.data.model.ImageData
import utopia.ikbal.simplemovieapplication.extensions.applySchedulers
import utopia.ikbal.simplemovieapplication.repository.ActorMovieRepository
import utopia.ikbal.simplemovieapplication.ui.base.BaseViewModel
import utopia.ikbal.simplemovieapplication.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ActorMovieViewModel
@Inject
constructor(private val actorMovieRepository: ActorMovieRepository) : BaseViewModel() {

    private val _actorLiveData = MutableLiveData<NetworkResult<ActorData>?>()
    private val _actorMovieLiveData =
        MutableLiveData<NetworkResult<List<ActorMovieData>?>>()
    private val _actorSeriesLiveData =
        MutableLiveData<NetworkResult<List<ActorSeriesCastData>?>>()
    private val _actorImagesLiveData = MutableLiveData<NetworkResult<List<ImageData>?>>()

    val actorLiveData: LiveData<NetworkResult<ActorData>?> = _actorLiveData
    val actorMovieLiveData: LiveData<NetworkResult<List<ActorMovieData>?>> =
        _actorMovieLiveData
    val actorSeriesLiveData: LiveData<NetworkResult<List<ActorSeriesCastData>?>> =
        _actorSeriesLiveData
    val actorImagesLiveData: LiveData<NetworkResult<List<ImageData>?>> = _actorImagesLiveData

    fun getDetails(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorDetails(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _actorLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorLiveData.value = NetworkResult.Data(it)
            }, {
                _actorLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getMoviesAsActor(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorMovies(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _actorMovieLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorMovieLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _actorMovieLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getActorSeries(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorSeries(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _actorSeriesLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorSeriesLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _actorSeriesLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getImages(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorImages(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _actorImagesLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorImagesLiveData.value = NetworkResult.Data(it.profiles)
            }, {
                _actorImagesLiveData.value = NetworkResult.Error(it)
            })
        )
    }
}