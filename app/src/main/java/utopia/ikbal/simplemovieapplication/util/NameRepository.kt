package utopia.ikbal.simplemovieapplication.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NameRepository {

    fun saveString(name: String): Completable

    fun saveBoolean(name: Boolean): Completable

    fun getString(): Observable<String>

    fun getBoolean(): Observable<Boolean>

    fun clear(): Completable
}