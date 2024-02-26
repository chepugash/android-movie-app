package com.practice.feature.home_impl.domain.usecase

import com.practice.feature.home_api.model.HomeEntity
import com.practice.feature.home_api.repository.HomeRepository
import com.practice.feature.home_api.usecase.GetFilmsUseCase

class GetFilmsUseCaseImpl(
    private val repository: HomeRepository
): GetFilmsUseCase {

    override suspend fun invoke(): List<HomeEntity>? = repository.getFilms()
}
