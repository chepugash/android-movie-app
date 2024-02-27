package com.practice.feature.auth_impl.domain.usecase

import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_api.usecase.CheckIsAuthorizedUseCase

class CheckIsAuthorizedUseCaseImpl(
    private val repository: AuthRepository
) : CheckIsAuthorizedUseCase {

    override suspend fun invoke(): Boolean = repository.getUser() != null
}
