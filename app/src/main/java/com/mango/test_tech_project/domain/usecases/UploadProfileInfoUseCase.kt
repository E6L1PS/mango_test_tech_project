package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.domain.repository.UserRepository
import javax.inject.Inject

class UploadProfileInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute() {
        userRepository.getCurrentUser()
    }
}