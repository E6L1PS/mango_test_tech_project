package com.mango.test_tech_project.di

import android.content.Context
import androidx.room.Room
import com.mango.test_tech_project.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext application: Context) =
        Room.databaseBuilder(application, AppDatabase::class.java, "mango_db").build()

    @Singleton
    @Provides
    fun provideUserInfoDao(db: AppDatabase) = db.getUserInfoDao()

}