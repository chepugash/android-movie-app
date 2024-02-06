package com.practice.feature.detail_api.model


import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailFilmEntity(
    @SerialName("countries")
    val countries: ImmutableList<Country?>?,
    @SerialName("coverUrl")
    val coverUrl: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("filmLength")
    val filmLength: Int?,
    @SerialName("genres")
    val genres: ImmutableList<Genre?>?,
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
class Country(
    @SerialName("country")
    val country: String?
)

@Serializable
class Genre(
    @SerialName("genre")
    val genre: String?
)
