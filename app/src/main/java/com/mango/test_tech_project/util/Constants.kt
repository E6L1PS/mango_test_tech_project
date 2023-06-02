package com.mango.test_tech_project.util

object Constants {
    const val BASE_URL = "https://plannerok.ru/"
    const val JWT_KEY = "jwt"

    val excludedUrls = listOf(
        "/api/v1/users/register", "/api/v1/users/send-auth-code", "/api/v1/users/check-auth-code",
    )
}