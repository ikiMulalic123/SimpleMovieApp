package utopia.ikbal.simplemovieapplication.util

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import utopia.ikbal.simplemovieapplication.extensions.clearSharedPreferences
import utopia.ikbal.simplemovieapplication.extensions.editSharedPreferences

class SharedPreferenceRepo(preferences: SharedPreferences) : NameRepository {

    private val prefSubject = BehaviorSubject.createDefault(preferences)

    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
            prefSubject.onNext(sharedPreferences)
        }

    override fun saveStringToSharedPreference(name: String): Completable = prefSubject
        .firstOrError()
        .editSharedPreferences {
            putString(KEY_STRING_NAME, name)
        }

    override fun saveBooleanToSharedPreference(name: Boolean): Completable =
        prefSubject.firstOrError()
            .editSharedPreferences {
                putBoolean(KEY_BOOLEAN_NAME, name)
            }

    override fun getStringFromSharedPreference(): Observable<String> =
        prefSubject.map { it.getString(KEY_STRING_NAME, "")}

    override fun getBooleanFromSharedPreference(): Observable<Boolean> =
        prefSubject.map { it.getBoolean(KEY_BOOLEAN_NAME, false) }

    override fun clearFromSharedPreference(): Completable {
        return prefSubject.firstOrError()
            .clearSharedPreferences {
                remove(KEY_STRING_NAME)
            }
    }

    companion object {

        private const val KEY_STRING_NAME = "session_id"
        private const val KEY_BOOLEAN_NAME = "isUserLogged"
        private const val MY_SHARED_PREFERENCE = "utopia.ikbal.simplemovieapplication.preference"

        fun create(context: Context): SharedPreferenceRepo {
            val preferences =
                context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            return SharedPreferenceRepo(preferences)
        }
    }

    init {
        preferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }
}