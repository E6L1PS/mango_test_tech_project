package com.mango.test_tech_project.data.db.dao

import androidx.room.*
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user WHERE id = :id")
    fun findUserInfo(id: Int): Flow<UserInfoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

    @Transaction
    @Query("UPDATE user SET avatar = :avatar, birthday = :birthday, city = :city, instagram = :instagram, name = :name, status = :status, vk = :vk WHERE id = :id")
    suspend fun updateUserInfo(
        id: Int,
        avatar: String,
        birthday: String,
        city: String,
        instagram: String,
        name: String,
        status: String,
        vk: String,
    )

    @Query("DELETE FROM user")
    suspend fun deleteAllUserInfo()

}
