package com.practice.feature.home_impl.data.datasource.remote

import retrofit2.http.GET

interface HomeApi {

    @GET("films?order=RATING&type=FILM&ratingFrom=7&ratingTo=10&yearFrom=2023&yearTo=2024&page=2")
    suspend fun getFilms(): HomeResponseEntity
}
