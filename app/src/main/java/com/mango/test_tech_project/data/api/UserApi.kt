package com.mango.test_tech_project.data.api

import com.mango.test_tech_project.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("api/v1/users/send-auth-code/")
    suspend fun signIn(@Body request: PhoneBase): Response<Success>

    @POST("api/v1/users/register-auth-code/")
    suspend fun signUp(@Body request: RegisterIn): Response<Token>

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCode): Response<LoginOut>

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshToken): Response<Token>

    @GET("api/v1/users/me/")
    suspend fun getCurrentUser(): Response<GetCurrentUserProfile>

    @PUT("api/v1/users/me/")
    suspend fun updateCurrentUser(@Body request: UserUpdate): Response<UserUpdateSend>

}