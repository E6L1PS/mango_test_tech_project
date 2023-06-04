package com.mango.test_tech_project.presentation.sign_up_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.model.RegisterIn
import com.mango.test_tech_project.domain.usecases.SignUpUseCase
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _id = MutableStateFlow<Resource<Int?>>(Resource.loading())
    val id = _id.asStateFlow()

    fun signUp(registerIn: RegisterIn) {
        viewModelScope.launch {
            _id.emit(signUpUseCase.execute(registerIn))
        }
    }

    fun updateCurrentUser(id: Int) {
        sharedPreferences.edit()
            .putInt(Constants.CURRENT_USER_ID, id)
            .apply()
    }

}