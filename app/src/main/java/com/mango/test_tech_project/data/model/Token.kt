package com.mango.test_tech_project.data.model

data class Token(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
)