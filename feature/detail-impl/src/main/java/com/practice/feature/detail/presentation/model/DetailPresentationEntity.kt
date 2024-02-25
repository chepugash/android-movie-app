package com.practice.feature.detail.presentation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Immutable
data class DetailPresentationEntity(
    val countries: ImmutableList<String>?,
    val description: String,
    val filmLength: String,
    val genres: ImmutableList<String?>?,
    val kinopoiskId: Int?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val ratingAgeLimits: String,
    val ratingImdb: Double?,
    val ratingKinopoisk: Double?,
    val slogan: String?,
    val webUrl: String?,
    val year: String
) {

    companion object {
        fun preview(): DetailPresentationEntity =
            DetailPresentationEntity(
                countries = immutableListOf("США", "Великобритания", "Испания"),
                description = "Афганистан, март 2018 года. Во время спецоперации по поиску оружия талибов отряд сержанта армии США Джона Кинли попадает в засаду. В живых остаются только сам Джон, получивший ранение, и местный переводчик Ахмед, который сотрудничает с американцами. Очнувшись на родине, Кинли не помнит, как ему удалось выжить, но понимает, что именно Ахмед спас ему жизнь, протащив на себе через опасную территорию. Теперь чувство вины не даёт Джону покоя, и он решает вернуться за Ахмедом и его семьёй, которых в Афганистане усиленно ищут талибы.",
                filmLength = "123",
                genres = immutableListOf("триллер", "драма", "боевик", "военный", "история"),
                kinopoiskId = 927898,
                nameOriginal = "The Covenant",
                nameRu = "Переводчик",
                posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/927898.jpg",
                ratingAgeLimits = "r",
                ratingImdb = 7.5,
                ratingKinopoisk = 7.4,
                slogan = "Попробуй переведи",
                webUrl = "https://www.kinopoisk.ru/film/927898/",
                year = "2024"
            )
    }
}
