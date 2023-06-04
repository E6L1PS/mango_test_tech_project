package com.mango.test_tech_project.presentation.splash_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.mango.test_tech_project.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    //TODO check jwt
    fun isAuthenticated() =  sharedPreferences.getString(Constants.JWT_ACCESS_KEY, null).isNullOrEmpty() ||
        sharedPreferences.getString(Constants.JWT_REFRESH_KEY, null).isNullOrEmpty()
}