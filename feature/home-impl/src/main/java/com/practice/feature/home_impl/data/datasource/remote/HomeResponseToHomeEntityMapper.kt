package com.practice.feature.home_impl.data.datasource.remote

import com.practice.feature.home_api.model.HomeEntity

object HomeResponseToHomeEntityMapper {

    operator fun invoke(response: HomeResponseEntity): List<HomeEntity>? {
        val list = mutableListOf<HomeEntity>()

        return response.items?.map { item ->
            item.toEntity()
        }?.toList()

    }

    private fun Item.toEntity(): HomeEntity =
        HomeEntity(
            kinopoiskId = this.kinopoiskId,
            nameRu = this.nameRu,
            nameOriginal = this.nameOriginal,
            ratingKinopoisk = this.ratingKinopoisk,
            year = this.year,
            posterUrlPreview = this.posterUrlPreview
        )
}
