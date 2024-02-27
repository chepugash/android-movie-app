package com.practice.feature.auth_impl.domain.usecase

import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_api.usecase.CheckIsCodeCorrectUseCase

class CheckIsCodeCorrectUseCaseImpl(
    private val repository: AuthRepository
) : CheckIsCodeCorrectUseCase {

    override suspend fun invoke(phone: String, code: String): Boolean =
        repository.checkIsCodeCorrect(phone, code)
}
