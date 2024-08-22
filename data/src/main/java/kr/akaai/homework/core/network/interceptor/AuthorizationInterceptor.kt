package kr.akaai.homework.core.network.interceptor


import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(
                request.newBuilder()
                    .build()
            )
        } catch (e: Exception) {
            chain.proceed(request)
        }
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }
}