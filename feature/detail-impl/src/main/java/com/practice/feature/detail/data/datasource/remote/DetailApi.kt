package com.practice.feature.detail.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("films/{id}")
    suspend fun getFilmById(@Path("id") id: Int): DetailResponseEntity
}
