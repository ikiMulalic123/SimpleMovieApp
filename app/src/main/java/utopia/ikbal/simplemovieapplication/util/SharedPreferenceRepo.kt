package utopia.ikbal.simplemovieapplication.util

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.MY_SHARED_PREFERENCE
import utopia.ikbal.simplemovieapplication.extensions.clearSharedPreferences
import utopia.ikbal.simplemovieapplication.extensions.editSharedPreferences

class SharedPreferenceRepo(context: Context) : NameRepository {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
    private val prefSubject = BehaviorSubject.createDefault(preferences)

    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
            prefSubject.onNext(sharedPreferences)
        }

    override fun saveName(name: String): Completable = prefSubject
        .firstOrError()
        .editSharedPreferences {
            putString(KEY_NAME, name)
        }

    override fun name(): Observable<String> =
        prefSubject.map { it.getString(KEY_NAME, "") }

    override fun clear(): Completable {
        return prefSubject.firstOrError()
            .clearSharedPreferences {
                remove(KEY_NAME)
            }
    }

    companion object {

        fun create(context: Context): SharedPreferenceRepo {
            val preferences =
                context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            return SharedPreferenceRepo(context)
        }

        private const val KEY_NAME = "key_name"
    }

    init {
        preferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }
}