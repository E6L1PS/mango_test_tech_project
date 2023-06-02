package com.mango.test_tech_project.data.db.dao

import androidx.room.*
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user")
    fun findUserInfo(): Flow<UserInfoEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

}
