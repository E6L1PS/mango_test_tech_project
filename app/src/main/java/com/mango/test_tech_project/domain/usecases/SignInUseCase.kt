package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.data.model.PhoneBase
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Resource
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun execute(phoneBase: PhoneBase): Resource<Boolean> {
        return userRepository.signIn(phoneBase)
    }

}