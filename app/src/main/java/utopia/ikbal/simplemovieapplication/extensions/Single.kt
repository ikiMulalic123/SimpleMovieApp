package utopia.ikbal.simplemovieapplication.extensions

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Single<T>.applySchedulers(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun Single<SharedPreferences>.editSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }

fun Single<SharedPreferences>.clearSharedPreferences(batch: SharedPreferences.Editor.() -> Unit): Completable =
    flatMapCompletable {
        Completable.fromAction {
            it.edit().also(batch).apply()
        }
    }

fun <T> Observable<T>.applySchedulers(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())