package com.mango.test_tech_project.presentation.profile_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.domain.usecases.GetProfileInfoUseCase
import com.mango.test_tech_project.domain.usecases.UploadProfileInfoUseCase
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
) : ViewModel() {

    private val _profileInfo = MutableStateFlow<UserInfoEntity?>(null)
    val profileInfo = _profileInfo.asStateFlow()


    init {
        getInfo()
    }

    private fun getInfo() {

        Log.d("InfoProfileLog", "init")
        viewModelScope.launch {
            _profileInfo.emitAll(getProfileInfoUseCase.execute())
        }
    }

    fun uploadProfileInfoUseCase() {
        viewModelScope.launch {
            uploadProfileInfoUseCase.execute()
        }
    }

}