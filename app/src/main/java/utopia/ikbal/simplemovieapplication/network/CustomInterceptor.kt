package utopia.ikbal.simplemovieapplication.network

import okhttp3.Interceptor
import okhttp3.Response
import utopia.ikbal.simplemovieapplication.data.Constants.Companion.TOKEN

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", TOKEN)
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}