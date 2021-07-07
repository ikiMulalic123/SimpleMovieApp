package utopia.ikbal.simplemovieapplication.network

import okhttp3.Interceptor
import okhttp3.Response

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
        private const val TOKEN = "d3e2a8685607404b0b0b0c92bef8c32a"
    }
}