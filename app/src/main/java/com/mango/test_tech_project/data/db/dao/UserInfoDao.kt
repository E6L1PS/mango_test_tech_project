package com.mango.test_tech_project.data.db.dao

import androidx.room.*
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.data.model.UserUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user")
    fun findUserInfo(): Flow<UserInfoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

    /*@Query("UPDATE user SET avatar = :avatar, birthday = :birthday, city = :city, instagram = :instagram, name = :name, status = :status, vk = :vk WHERE username = :username")
    fun updateUser(avatar: String, birthday: String, city: String, instagram: String, name: String, status: String, vk: String, username: String)
*/
    @Query("DELETE FROM user")
    suspend fun deleteAllUserInfo()

}
