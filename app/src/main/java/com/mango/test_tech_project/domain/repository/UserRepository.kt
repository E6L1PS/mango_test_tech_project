package com.mango.test_tech_project.domain.repository

import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.data.model.*
import com.mango.test_tech_project.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signIn(phoneBase: PhoneBase): Resource<Boolean>

    suspend fun signUp(registerIn: RegisterIn): Resource<Int?>

    suspend fun checkAuthCode(checkAuthCode: CheckAuthCode): Resource<LoginOut?>

    suspend fun refreshToken(): Resource<Unit>

    suspend fun getCurrentUser()

    fun getUserInfo(id: Int): Flow<Resource<UserInfoEntity?>>

    suspend fun updateCurrentUser(id: Int, userUpdate: UserUpdate): Resource<UserUpdateSend?>
}
