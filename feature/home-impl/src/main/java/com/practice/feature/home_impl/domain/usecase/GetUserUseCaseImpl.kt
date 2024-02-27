package com.practice.feature.home_impl.domain.usecase

import com.practice.feature.home_api.repository.HomeRepository
import com.practice.feature.home_api.usecase.GetUserUseCase

class GetUserUseCaseImpl(
    private val repository: HomeRepository
) : GetUserUseCase {

    override suspend fun invoke(): String = repository.getUser()
}
