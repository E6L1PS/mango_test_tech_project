package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.data.model.RegisterIn
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Resource
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun execute(registerIn: RegisterIn): Resource<Unit> {
        return userRepository.signUp(registerIn)
    }
}