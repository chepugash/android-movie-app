package com.practice.feature.home_impl.domain.usecase

import com.practice.feature.home_api.repository.HomeRepository
import com.practice.feature.home_api.usecase.SignOutUseCase

class SignOutUseCaseImpl(
    private val repository: HomeRepository
) : SignOutUseCase {

    override suspend fun invoke(phone: String) = repository.deleteUser(phone)
}
