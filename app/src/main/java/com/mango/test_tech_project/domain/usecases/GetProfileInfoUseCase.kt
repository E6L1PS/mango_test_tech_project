package com.mango.test_tech_project.domain.usecases

import com.mango.test_tech_project.data.db.entity.UserInfoEntity
import com.mango.test_tech_project.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun execute(): Flow<UserInfoEntity> {
        return repository.getUserInfo()
    }
}