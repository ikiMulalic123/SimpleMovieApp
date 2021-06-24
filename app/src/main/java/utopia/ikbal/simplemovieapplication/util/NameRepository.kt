package utopia.ikbal.simplemovieapplication.util

import io.reactivex.Completable
import io.reactivex.Observable

interface NameRepository {

    fun saveName(name: String): Completable

    fun name(): Observable<String>

    fun clear(): Completable
}