package com.practice.feature.detail_api.model


data class DetailFilmEntity(
    val countries: List<CountryEntity?>?,
    val coverUrl: String?,
    val description: String?,
    val filmLength: Int?,
    val genres: List<GenreEntity?>?,
    val kinopoiskId: Int?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingAgeLimits: String?,
    val ratingFilmCritics: Double?,
    val ratingImdb: Double?,
    val ratingKinopoisk: Double?,
    val ratingMpaa: String?,
    val shortDescription: String?,
    val slogan: String?,
    val type: String?,
    val webUrl: String?,
    val year: Int?
)

class CountryEntity(
    val country: String?
)

class GenreEntity(
    val genre: String?
)
