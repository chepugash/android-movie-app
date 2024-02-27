package com.practice.feature.auth_impl.domain.usecase

import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_api.usecase.SendCodeToUserUseCase

class SendCodeToUserUseCaseImpl(
    private val repository: AuthRepository
) : SendCodeToUserUseCase {

    override suspend fun invoke(phone: String) = repository.sendCodeToUser(phone)
}
