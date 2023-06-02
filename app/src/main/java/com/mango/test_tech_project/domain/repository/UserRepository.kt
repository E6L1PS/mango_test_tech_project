package com.mango.test_tech_project.domain.repository

import com.mango.test_tech_project.data.model.*
import com.mango.test_tech_project.util.Resource

interface UserRepository {

    suspend fun signIn(phoneBase: PhoneBase): Resource<Success>

    suspend fun signUp(registerIn: RegisterIn): Resource<Unit>

    suspend fun checkAuth(checkAuthCode: CheckAuthCode): Resource<LoginOut>

    suspend fun refreshToken(refreshToken: RefreshToken): Resource<RefreshToken>

    suspend fun getCurrentUser(): Resource<GetCurrentUserProfile>

    suspend fun updateCurrentUser(userUpdate: UserUpdate): Resource<UserUpdateSend>
}
