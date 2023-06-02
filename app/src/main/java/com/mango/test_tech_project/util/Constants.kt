package com.mango.test_tech_project.util

object Constants {
    const val BASE_URL = "https://plannerok.ru/"
    const val JWT_ACCESS_KEY = "access_token"
    const val JWT_REFRESH_KEY = "refresh_token"

    val excludedUrls = listOf(
        "/api/v1/users/register/", "/api/v1/users/send-auth-code/", "/api/v1/users/check-auth-code/",
    )
}