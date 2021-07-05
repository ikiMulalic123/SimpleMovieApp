package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN
import utopia.ikbal.simplemovieapplication.data.model.CreateSessionData
import utopia.ikbal.simplemovieapplication.data.model.LoginData
import utopia.ikbal.simplemovieapplication.data.model.TokenStringData
import utopia.ikbal.simplemovieapplication.network.LoginApi
import utopia.ikbal.simplemovieapplication.util.SharedPreferenceRepo

class LoginRepository
constructor(
    private val loginApi: LoginApi,
) {

    fun getToken() = loginApi.getToken(TOKEN)

    fun createSession(body: TokenStringData) = loginApi.createSession(TOKEN, body)

    fun validateTokenLogin(body: LoginData) = loginApi.validateTokenLogin(TOKEN, body)
}