package com.mango.test_tech_project.util

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sharedPreferences.getString(Constants.JWT_ACCESS_KEY, null)
        // TODO refresh token logic
        val refreshToken = sharedPreferences.getString(Constants.JWT_REFRESH_KEY, null)
        val request = chain.request()

        Log.i("okHttp.JWT", accessToken.toString())
        Log.i("okHttp.JWT", request.url.encodedPath)

        val url = request.url.encodedPath

        val authenticatedRequest = if (isAuthRequired(url) && accessToken != null) {
            Log.i("okHttp.JWT", "Запрос с токеном")

            if (url == "/api/v1/users/refresh-token") {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $refreshToken")
                    .build()
            } else {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            }

        } else {
            Log.d("okHttp.JWT", "Запрос без токена")
            request
        }
        return chain.proceed(authenticatedRequest)
    }

    private fun isAuthRequired(url: String): Boolean {
        // Проверяем, нужно ли добавлять токен к текущему URL
        return !Constants.excludedUrls.contains(url)
    }

}