package com.mango.test_tech_project.data.repository

import android.content.SharedPreferences
import com.mango.test_tech_project.data.api.UserApi
import com.mango.test_tech_project.data.model.*
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val sharedPreferences: SharedPreferences,
) : UserRepository {

    override suspend fun signIn(phoneBase: PhoneBase): Resource<Success> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(registerIn: RegisterIn): Resource<Unit> {
        return try {
            val response = api.signUp(registerIn)

            if (response.isSuccessful) {
                sharedPreferences.edit()
                    .putString(Constants.JWT_ACCESS_KEY, response.body()?.accessToken)
                    .putString(Constants.JWT_REFRESH_KEY, response.body()?.refreshToken)
                    .apply()
                Resource.success(Unit)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                // TODO refreshToken()
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun checkAuth(checkAuthCode: CheckAuthCode): Resource<LoginOut> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(refreshToken: RefreshToken): Resource<RefreshToken> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Resource<GetCurrentUserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrentUser(userUpdate: UserUpdate): Resource<UserUpdateSend> {
        TODO("Not yet implemented")
    }


}