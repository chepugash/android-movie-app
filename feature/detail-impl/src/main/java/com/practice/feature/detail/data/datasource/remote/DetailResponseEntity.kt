package com.practice.feature.detail.data.datasource.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponseEntity(
    @SerialName("countries")
    val countries: List<CountryResponseEntity?>?,
    @SerialName("coverUrl")
    val coverUrl: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("filmLength")
    val filmLength: Int?,
    @SerialName("genres")
    val genres: List<GenreResponseEntity?>?,
    @SerialName("kinopoiskId")
    val kinopoiskId: Int?,
    @SerialName("nameOriginal")
    val nameOriginal: String?,
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("posterUrl")
    val posterUrl: String?,
    @SerialName("posterUrlPreview")
    val posterUrlPreview: String?,
    @SerialName("ratingAgeLimits")
    val ratingAgeLimits: String?,
    @SerialName("ratingFilmCritics")
    val ratingFilmCritics: Double?,
    @SerialName("ratingImdb")
    val ratingImdb: Double?,
    @SerialName("ratingKinopoisk")
    val ratingKinopoisk: Double?,
    @SerialName("ratingMpaa")
    val ratingMpaa: String?,
    @SerialName("shortDescription")
    val shortDescription: String?,
    @SerialName("slogan")
    val slogan: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("webUrl")
    val webUrl: String?,
    @SerialName("year")
    val year: Int?
)

@Serializable
class CountryResponseEntity(
    @SerialName("country")
    val country: String?
)

@Serializable
class GenreResponseEntity(
    @SerialName("genre")
    val genre: String?
)
