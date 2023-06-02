package com.mango.test_tech_project.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mango.test_tech_project.data.api.UserApi
import com.mango.test_tech_project.util.AuthTokenInterceptor
import com.mango.test_tech_project.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(converter: GsonConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(converter)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideClient(interceptor: HttpLoggingInterceptor, authTokenInterceptor: AuthTokenInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authTokenInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(sharedPreferences: SharedPreferences): AuthTokenInterceptor =
        AuthTokenInterceptor(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

}