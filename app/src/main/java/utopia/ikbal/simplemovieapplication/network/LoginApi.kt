package utopia.ikbal.simplemovieapplication.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import utopia.ikbal.simplemovieapplication.data.model.*

interface LoginApi {

    @GET(TOKEN)
    fun getToken(): Single<TokenData>

    @POST(NEW_SESSION)
    fun createSession(@Body body: TokenStringData): Single<SessionData>

    @POST(LOGIN_SESSION)
    fun validateTokenLogin(@Body body: LoginData): Single<LoginResponseData>

    companion object {
        private const val NEW = "new"
        private const val _TOKEN = "token/"
        private const val SESSION = "session/"
        private const val TOKEN = "token/$NEW"
        private const val NEW_SESSION = "$SESSION$NEW"
        private const val LOGIN_SESSION = "${_TOKEN}validate_with_login"
    }
}