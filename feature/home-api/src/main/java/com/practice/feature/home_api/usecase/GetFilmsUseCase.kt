package com.practice.feature.home_api.usecase

import com.practice.feature.home_api.model.HomeEntity

interface GetFilmsUseCase{

    suspend operator fun invoke(): List<HomeEntity>?
}
