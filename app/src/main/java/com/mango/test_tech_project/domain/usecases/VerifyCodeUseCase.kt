package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.data.model.CheckAuthCode
import com.mango.test_tech_project.data.model.PhoneBase
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Resource
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend fun execute(checkAuthCode: CheckAuthCode): Resource<Boolean> {
        return userRepository.checkAuthCode(checkAuthCode)
    }
}