package com.mango.test_tech_project.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mango.test_tech_project.data.db.dao.UserInfoDao
import com.mango.test_tech_project.data.db.entity.UserInfoEntity

@Database(
    version = 1,
    entities = [
        UserInfoEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserInfoDao(): UserInfoDao
}