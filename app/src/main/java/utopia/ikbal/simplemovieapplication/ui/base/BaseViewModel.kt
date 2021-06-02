package utopia.ikbal.simplemovieapplication.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(protected val scheduler: Scheduler = Schedulers.io()) :
    ViewModel() {
    protected val compositeDisposable by lazy {
        CompositeDisposable()
    }
    protected fun addToDisposable(disposable: Disposable) = compositeDisposable.add(disposable)
    protected fun clearDisposable() = compositeDisposable.clear()
    override fun onCleared() {
        super.onCleared()
        dispose()
    }
    private fun dispose() =
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose() else Unit
}