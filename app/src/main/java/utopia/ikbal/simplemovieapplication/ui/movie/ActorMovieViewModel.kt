package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorImageData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorSeriesCastData
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
    private val _actorMovieAsActorLiveData =
        MutableLiveData<NetworkResult<List<ActorMovieAsActorData>?>>()
    private val _actorSeriesAsActorLiveData =
        MutableLiveData<NetworkResult<List<ActorSeriesCastData>?>>()
    private val _actorImagesLiveData = MutableLiveData<NetworkResult<List<ActorImageData>?>>()

    val actorLiveData: LiveData<NetworkResult<ActorData>?> = _actorLiveData
    val actorMovieAsActorLiveData: LiveData<NetworkResult<List<ActorMovieAsActorData>?>> =
        _actorMovieAsActorLiveData
    val actorSeriesAsActorLiveData: LiveData<NetworkResult<List<ActorSeriesCastData>?>> =
        _actorSeriesAsActorLiveData
    val actorImagesLiveData: LiveData<NetworkResult<List<ActorImageData>?>> = _actorImagesLiveData

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
                _actorMovieAsActorLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorMovieAsActorLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _actorMovieAsActorLiveData.value = NetworkResult.Error(it)
            })
        )
    }

    fun getActorSeries(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorSeries(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                _actorSeriesAsActorLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                _actorSeriesAsActorLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _actorSeriesAsActorLiveData.value = NetworkResult.Error(it)
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