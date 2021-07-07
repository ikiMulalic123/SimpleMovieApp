package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.util.SharedPreferenceRepo

class SharedPreferenceRepository
constructor(
    private val sharedPreferenceRepo: SharedPreferenceRepo
) {

    fun saveStringToSharedPreference(name: String) =
        sharedPreferenceRepo.saveStringToSharedPreference(name)

    fun saveBooleanToSharedPreference(name: Boolean) =
        sharedPreferenceRepo.saveBooleanToSharedPreference(name)

    fun getStringFromSharedPreference() =
        sharedPreferenceRepo.getStringFromSharedPreference()

    fun getBooleanFromSharedPreference() = sharedPreferenceRepo.getBooleanFromSharedPreference()

    fun clearFromSharedPreference() = sharedPreferenceRepo.clearFromSharedPreference()
}