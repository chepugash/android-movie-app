package com.practice.feature.detail.data.datasource.remote

import retrofit2.http.GET

interface DetailApi {

    @GET("{id}")
    suspend fun getFilmById(id: Int): DetailResponseEntity
}
