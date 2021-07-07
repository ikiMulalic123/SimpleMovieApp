package utopia.ikbal.simplemovieapplication.repository

import utopia.ikbal.simplemovieapplication.data.model.LoginData
import utopia.ikbal.simplemovieapplication.data.model.TokenStringData
import utopia.ikbal.simplemovieapplication.network.LoginApi

class LoginRepository
constructor(
    private val loginApi: LoginApi
) {

    fun getToken() = loginApi.getToken()

    fun createSession(body: TokenStringData) = loginApi.createSession(body)

    fun validateTokenLogin(body: LoginData) = loginApi.validateTokenLogin(body)
}