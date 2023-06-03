package com.mango.test_tech_project.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.mango.test_tech_project.data.api.UserApi
import com.mango.test_tech_project.data.db.dao.UserInfoDao
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.data.model.*
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val dao: UserInfoDao,
    private val sharedPreferences: SharedPreferences,
) : UserRepository {

    override suspend fun signIn(phoneBase: PhoneBase): Resource<Boolean> {
        return try {
            val response = api.signIn(phoneBase)

            if (response.isSuccessful) {
                Resource.success(response.body()?.is_success ?: false)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun signUp(registerIn: RegisterIn): Resource<Unit> {
        return try {
            val response = api.signUp(registerIn)

            if (response.isSuccessful) {
                sharedPreferences.edit()
                    .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                    .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                    .apply()

                dao.deleteAllUserInfo()
                Resource.success(Unit)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun checkAuthCode(checkAuthCode: CheckAuthCode): Resource<Boolean> {
        return try {
            val response = api.checkAuthCode(checkAuthCode)

            if (response.isSuccessful) {
                sharedPreferences.edit()
                    .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                    .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                    .apply()
                Resource.success(response.body()?.is_user_exists ?: false)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun refreshToken(): Resource<Unit> {
        return try {
            val refreshToken = sharedPreferences.getString(Constants.JWT_REFRESH_KEY, null)
            val response = api.refreshToken(RefreshToken(refreshToken!!))

            if (response.isSuccessful) {
                sharedPreferences.edit()
                    .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                    .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                    .apply()
                Resource.success(Unit)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun getCurrentUser() {
       try {
            val response = api.getCurrentUser()

            if (response.isSuccessful) {
                Log.d("InfoProfileLog", "isSuccessful")
                val info = response.body()!!.profile_data.toUserInfoEntity()

                Log.d("InfoProfileLog", info.toString())/*
                dao.insertUserInfo(UserInfoEntity(1,"d", "d","d", "d","d",
                    "d",1, "d",true, "d","d", "d","d"))*/
                dao.insertUserInfo(info)
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                refreshToken()
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override fun getUserInfo(): Flow<UserInfoEntity> {
        return dao.findUserInfo()
    }

    override suspend fun updateCurrentUser(userUpdate: UserUpdate): Resource<UserUpdateSend?> {
        return try {
            val response = api.updateCurrentUser(userUpdate)

            if (response.isSuccessful) {
                getCurrentUser()
                Resource.success(response.body())
            } else {
                Resource.error(response.message(), null)
            }

        } catch (e: HttpException) {
            if (e.code() == 401) {
                refreshToken()
                Resource.error(e.message(), null)
            } else {
                Resource.error(e.message(), null)
            }
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }


}