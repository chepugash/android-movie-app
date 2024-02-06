package com.practice.feature.detail_api.usecase

import com.practice.feature.detail_api.model.DetailFilmEntity

interface GetFilmByIdUseCase {

    suspend operator fun invoke(id: Int): DetailFilmEntity
}
