package com.practice.feature.detail_api.repository

import com.practice.feature.detail_api.model.DetailFilmEntity

interface DetailRepository {

    suspend fun getFilmById(id: Int): DetailFilmEntity
}
