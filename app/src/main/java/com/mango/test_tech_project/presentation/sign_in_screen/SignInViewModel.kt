package com.mango.test_tech_project.presentation.sign_in_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.model.CheckAuthCode
import com.mango.test_tech_project.data.model.PhoneBase
import com.mango.test_tech_project.domain.usecases.SignInUseCase
import com.mango.test_tech_project.domain.usecases.VerifyCodeUseCase
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase,
) : ViewModel() {
    private val _isSuccess = MutableStateFlow<Resource<Boolean>>(Resource.loading())
    val isSuccess = _isSuccess.asStateFlow()

    private val _isUserExists = MutableStateFlow<Resource<Boolean>>(Resource.loading())
    val isUserExists = _isUserExists.asStateFlow()

    fun signIn(phoneBase: PhoneBase) {
        viewModelScope.launch {
            _isSuccess.emit(signInUseCase.execute(phoneBase))
        }
    }


    fun verifyCode(checkAuthCode: CheckAuthCode) {
        viewModelScope.launch {
            _isUserExists.emit(verifyCodeUseCase.execute(checkAuthCode))
        }
    }
}