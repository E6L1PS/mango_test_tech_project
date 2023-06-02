package com.mango.test_tech_project.di

import com.mango.test_tech_project.data.repository.UserRepositoryImpl
import com.mango.test_tech_project.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAuthRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}