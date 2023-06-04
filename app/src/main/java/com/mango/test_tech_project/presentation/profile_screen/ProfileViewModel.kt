package com.mango.test_tech_project.presentation.profile_screen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.domain.usecases.GetProfileInfoUseCase
import com.mango.test_tech_project.domain.usecases.UploadProfileInfoUseCase
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val uploadProfileInfoUseCase: UploadProfileInfoUseCase,
    savedStateHandle: SavedStateHandle,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _profileInfo = MutableStateFlow<Resource<UserInfoEntity?>>(Resource.loading())
    val profileInfo = _profileInfo.asStateFlow()

    init {
        getInfo(savedStateHandle.get<Int>("id") ?: 0)
    }

    private fun getInfo(id: Int) {
        Log.d("InfoProfileLog", "$id")
        viewModelScope.launch {
            _profileInfo.emitAll(getProfileInfoUseCase.execute(
                sharedPreferences.getInt(Constants.CURRENT_USER_ID, -1)))
        }
    }

    fun uploadProfileInfoUseCase() {
        viewModelScope.launch {
            uploadProfileInfoUseCase.execute()
        }
    }

    fun clearTokens() {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

}