package com.practice.feature.home_impl.data.datasource.remote

import retrofit2.http.GET

interface HomeApi {

    @GET("films?page=5")
    suspend fun getFilms(): HomeResponseEntity
}
