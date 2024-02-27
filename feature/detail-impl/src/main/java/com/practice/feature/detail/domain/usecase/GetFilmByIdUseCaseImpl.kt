package com.practice.feature.detail.domain.usecase

import com.practice.feature.detail_api.model.DetailFilmEntity
import com.practice.feature.detail_api.repository.DetailRepository
import com.practice.feature.detail_api.usecase.GetFilmByIdUseCase

class GetFilmByIdUseCaseImpl(
    private val repository: DetailRepository
): GetFilmByIdUseCase {

    override suspend fun invoke(id: Int): DetailFilmEntity = repository.getFilmById(id)
}
