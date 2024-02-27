package com.practice.feature.auth_impl.domain.usecase

import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_api.usecase.SaveUserUseCase

class SaveUserUseCaseImpl(
    private val repository: AuthRepository
) : SaveUserUseCase {

    override suspend fun invoke(phone: String) = repository.saveUser(phone)
}
