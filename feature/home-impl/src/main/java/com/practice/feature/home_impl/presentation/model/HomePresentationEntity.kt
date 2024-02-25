package com.practice.feature.home_impl.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomePresentationEntity(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameOriginal: String?,
    val ratingKinopoisk: Double?,
    val year: String,
    val posterUrlPreview: String
) {

    companion object {
        fun preview(): HomePresentationEntity =
            HomePresentationEntity(
                kinopoiskId = 927898,
                nameRu = "Переводчик",
                nameOriginal = "The Convenant",
                ratingKinopoisk = 7.4,
                year = "2023",
                posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp/674243.jpg"
            )
    }
}
