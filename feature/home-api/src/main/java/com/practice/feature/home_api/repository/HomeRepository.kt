package com.practice.feature.home_api.repository

import com.practice.feature.home_api.model.HomeEntity

interface HomeRepository {

    suspend fun getFilms(): List<HomeEntity>?
}
