package com.practice.feature.home_impl.data.datasource.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseEntity(
    @SerialName("items")
    val items: List<Item>?,
    @SerialName("total")
    val total: Int?,
    @SerialName("totalPages")
    val totalPages: Int?
)

@Serializable
data class Item(
    @SerialName("kinopoiskId")
    val kinopoiskId: Int,
    @SerialName("nameOriginal")
    val nameOriginal: String?,
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("posterUrl")
    val posterUrlPreview: String,
    @SerialName("ratingKinopoisk")
    val ratingKinopoisk: Double?,
    @SerialName("year")
    val year: Int?
)

