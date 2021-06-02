package utopia.ikbal.simplemovieapplication.util

sealed class NetworkResult<out R> {
    data class Data<out T>(val data: T) : NetworkResult<T>()
    data class Error(val throwable: Throwable) :
        NetworkResult<Nothing>()
    object Empty : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    object Loaded : NetworkResult<Nothing>()
    override fun toString(): String {
        return when (this) {
            is Data<*> -> "Data[data=$data]"
            is Error -> "Error[throwable=${throwable.message}]"
            is Empty -> "Empty"
            is Loading -> "Loading"
            is Loaded -> "Loaded"
        }
    }
}
interface NetworkResultProcessor {
    fun <T> processNetworkResult(
        networkResult: NetworkResult<T>,
        showProgress: () -> Unit = {},
        hideProgress: () -> Unit = {},
        data: (T) -> Unit = {},
        noData: () -> Unit = {},
        onLoaded: () -> Unit = {},
        onError: (Throwable) -> Unit = { showGenericError(it.message.orEmpty()) }
    ) {
        when (networkResult) {
            is NetworkResult.Loading -> {
                showProgress()
            }
            is NetworkResult.Data -> {
                hideProgress()
                data(networkResult.data)
            }
            is NetworkResult.Empty -> {
                hideProgress()
                noData()
            }
            is NetworkResult.Error -> {
                hideProgress()
                onError(networkResult.throwable)
            }
            is NetworkResult.Loaded -> {
                hideProgress()
                onLoaded()
            }
        }
    }
    fun showGenericError(message: String)
}