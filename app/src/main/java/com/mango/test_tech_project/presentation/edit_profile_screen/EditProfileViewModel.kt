package com.mango.test_tech_project.presentation.edit_profile_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.model.UserUpdate
import com.mango.test_tech_project.data.model.UserUpdateSend
import com.mango.test_tech_project.domain.usecases.EditProfileUseCase
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userUpdateSend = MutableStateFlow<Resource<UserUpdateSend?>>(Resource.loading())
    val userUpdateSend = _userUpdateSend.asStateFlow()

    fun updateUserInfo( userUpdate: UserUpdate) {
        viewModelScope.launch {
            _userUpdateSend.emit(editProfileUseCase.execute(
                sharedPreferences.getInt(Constants.CURRENT_USER_ID, -1), userUpdate))
        }
    }
}