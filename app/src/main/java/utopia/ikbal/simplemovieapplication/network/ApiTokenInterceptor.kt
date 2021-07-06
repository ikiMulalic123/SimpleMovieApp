package utopia.ikbal.simplemovieapplication.network

import okhttp3.Interceptor
import okhttp3.Response
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN

class ApiTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter(API_KEY, TOKEN)
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "api_key"
    }
}