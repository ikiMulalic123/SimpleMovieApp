package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.util.SharedPreferenceRepo

class SharedPreferenceRepository
constructor(
    private val sharedPreferenceRepo: SharedPreferenceRepo
) {

    fun saveString(name : String) = sharedPreferenceRepo.saveString(name)

    fun saveBoolean(name : Boolean) = sharedPreferenceRepo.saveBoolean(name)

    fun getString ()  = sharedPreferenceRepo.getString()

    fun getBoolean() = sharedPreferenceRepo.getBoolean()

    fun clear() = sharedPreferenceRepo.clear()
}