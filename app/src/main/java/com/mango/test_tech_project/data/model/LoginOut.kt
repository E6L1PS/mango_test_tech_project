package com.mango.test_tech_project.data.model

data class LoginOut(
    val refresh_token: String,
    val access_token: String,
    val user_id: Int,
    val is_user_exists: Boolean,
)