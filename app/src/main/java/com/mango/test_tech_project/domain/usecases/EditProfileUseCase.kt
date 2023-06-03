package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.data.model.UserUpdate
import com.mango.test_tech_project.data.model.UserUpdateSend
import com.mango.test_tech_project.domain.repository.UserRepository
import com.mango.test_tech_project.util.Resource
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend fun execute(userUpdate: UserUpdate): Resource<UserUpdateSend?> {
        return userRepository.updateCurrentUser(userUpdate)
    }

}
