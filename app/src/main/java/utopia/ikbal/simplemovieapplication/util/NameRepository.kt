package utopia.ikbal.simplemovieapplication.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NameRepository {

    fun saveStringToSharedPreference(name: String): Completable

    fun saveBooleanToSharedPreference(name: Boolean): Completable

    fun getStringFromSharedPreference(): Observable<String>

    fun getBooleanFromSharedPreference(): Observable<Boolean>

    fun clearFromSharedPreference(): Completable
}