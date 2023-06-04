package com.mango.test_tech_project.data.repository

import android.content.ContentValues.TAG
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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

            when (response.code()) {
                200 -> {
                    Resource.success(response.body()?.is_success ?: false)
                }

                201 -> {
                    Resource.success(response.body()?.is_success ?: false)
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
            }

        } catch (e: HttpException) {
            Resource.error(e.message(), null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun signUp(registerIn: RegisterIn): Resource<Int?> {
        return try {
            val response = api.signUp(registerIn)

            when (response.code()) {
                201 -> {
                    sharedPreferences.edit()
                        .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                        .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                        .apply()
                    Resource.success(response.body()?.user_id)
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
            }
        } catch (e: HttpException) {
            Resource.error(e.message(), null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun checkAuthCode(checkAuthCode: CheckAuthCode): Resource<LoginOut?> {
        return try {
            val response = api.checkAuthCode(checkAuthCode)
            when (response.code()) {
                200 -> {
                    sharedPreferences.edit()
                        .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                        .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                        .apply()
                    Resource.success(response.body())
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
            }

        } catch (e: HttpException) {
            Resource.error(e.message(), null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override suspend fun refreshToken(): Resource<Unit> {
        return try {
            val refreshToken = sharedPreferences.getString(Constants.JWT_REFRESH_KEY, null)
            val response = api.refreshToken(RefreshToken(refreshToken!!))

            Log.d("UpdateLog401", "refresh")
            when (response.code()) {
                200 -> {
                    sharedPreferences.edit()
                        .putString(Constants.JWT_ACCESS_KEY, response.body()?.access_token)
                        .putString(Constants.JWT_REFRESH_KEY, response.body()?.refresh_token)
                        .apply()
                    Resource.success(Unit)
                }
                404 -> {
                    Resource.error(response.message(), null)
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
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

            when (response.code()) {
                200 -> {
                    Log.d("InfoProfileLog", "isSuccessful")
                    val info = response.body()!!.profile_data.toUserInfoEntity()
                    dao.insertUserInfo(info)
                }

                401 -> {
                    Log.d("UpdateLog401", "true")
                    refreshToken()
                    getCurrentUser()
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
            }


        } catch (e: HttpException) {
            Resource.error(e.message(), null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }

    override fun getUserInfo(id: Int): Flow<Resource<UserInfoEntity?>> {
        return dao.findUserInfo(id)
            .map { userInfoEntity ->
                Resource.success(userInfoEntity)
            }
            .catch { exception ->
                emit(Resource.error(exception.message!!))
            }
            .onStart {
                emit(Resource.loading())
            }
    }

    override suspend fun updateCurrentUser(id: Int, userUpdate: UserUpdate): Resource<UserUpdateSend?> {
        return try {
            val response = api.updateCurrentUser(userUpdate)

            when (response.code()) {
                200 -> {
                    dao.updateUserInfo(
                        id = id,
                        avatar = response.body()!!.avatars.avatar,
                        birthday = userUpdate.birthday,
                        name = userUpdate.name,
                        vk = userUpdate.vk,
                        instagram = userUpdate.instagram,
                        city = userUpdate.city,
                        status = userUpdate.status
                    )

                    Resource.success(response.body())
                }

                401 -> {
                    Log.d("UpdateLog401", "true")
                    refreshToken()
                    updateCurrentUser(id, userUpdate)
                }

                422 -> {
                    Resource.error(response.message(), null)
                }

                else -> {
                    Log.e(TAG, "Unexpected response code: ${response.code()}")
                    Resource.error(response.message(), null)
                }
            }

        } catch (e: HttpException) {
            Resource.error(e.message(), null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }


}