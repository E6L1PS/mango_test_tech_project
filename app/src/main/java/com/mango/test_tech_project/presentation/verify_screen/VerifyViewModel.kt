package com.mango.test_tech_project.presentation.verify_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.test_tech_project.data.model.CheckAuthCode
import com.mango.test_tech_project.domain.usecases.VerifyCodeUseCase
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val verifyCodeUseCase: VerifyCodeUseCase,
) : ViewModel() {

    private val _isUserExists = MutableStateFlow<Resource<Boolean>>(Resource.loading())
    val isUserExists = _isUserExists.asStateFlow()

    fun verifyCode(checkAuthCode: CheckAuthCode) {
        viewModelScope.launch {
            _isUserExists.emit(verifyCodeUseCase.execute(checkAuthCode))
        }
    }

}