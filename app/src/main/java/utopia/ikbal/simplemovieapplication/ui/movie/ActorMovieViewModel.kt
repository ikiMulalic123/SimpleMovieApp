package utopia.ikbal.simplemovieapplication.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import utopia.ikbal.simplemovieapplication.data.model.ActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsActorData
import utopia.ikbal.simplemovieapplication.data.model.ActorMovieAsProducerData
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
    private val _actorMovieAsProducerLiveData =
        MutableLiveData<NetworkResult<List<ActorMovieAsProducerData>?>>()
    private val _actorSeriesAsActorLiveData =
        MutableLiveData<NetworkResult<List<ActorSeriesCastData>?>>()

    val actorLiveData: LiveData<NetworkResult<ActorData>?> = _actorLiveData
    val actorMovieAsActorLiveData: LiveData<NetworkResult<List<ActorMovieAsActorData>?>> =
        _actorMovieAsActorLiveData
    val actorMovieAsProducerLiveData: LiveData<NetworkResult<List<ActorMovieAsProducerData>?>> =
        _actorMovieAsProducerLiveData
    val actorSeriesAsActorLiveData: LiveData<NetworkResult<List<ActorSeriesCastData>?>> =
        _actorSeriesAsActorLiveData

    var loading: Boolean = false

    fun getDetails(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorDetails(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _actorLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _actorLiveData.value = NetworkResult.Data(it)
            }, {
                _actorLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun getMoviesAsActor(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorMovies(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _actorMovieAsActorLiveData.value = NetworkResult.Loading
                _actorMovieAsProducerLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _actorMovieAsActorLiveData.value = NetworkResult.Data(it.cast)
                _actorMovieAsProducerLiveData.value = NetworkResult.Data(it.crew)
            }, {
                _actorMovieAsActorLiveData.value = NetworkResult.Error(it)
                _actorMovieAsProducerLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }

    fun getActorSeries(actorId: Int) {
        addToDisposable(actorMovieRepository.getActorSeries(actorId)
            .applySchedulers(scheduler)
            .doOnSubscribe {
                loading = true
                _actorSeriesAsActorLiveData.value = NetworkResult.Loading
            }
            .subscribe({
                loading = false
                _actorSeriesAsActorLiveData.value = NetworkResult.Data(it.cast)
            }, {
                _actorSeriesAsActorLiveData.value = NetworkResult.Error(it)
                loading = false
            })
        )
    }
}