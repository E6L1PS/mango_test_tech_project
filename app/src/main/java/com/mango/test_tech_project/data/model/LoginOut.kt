package com.mango.test_tech_project.data.model

data class LoginOut(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
    val isUserExists: Boolean,
)