package com.practice.feature.home_api.model

data class HomeEntity(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameOriginal: String?,
    val ratingKinopoisk: Double?,
    val year: Int?,
    val posterUrlPreview: String
)
