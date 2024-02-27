package com.practice.feature.home_impl.presentation.mapper

import com.practice.feature.home_api.model.HomeEntity
import com.practice.feature.home_impl.presentation.model.HomePresentationEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class HomeEntityToHomePresentationMapper {

    operator fun invoke(homeEntityList: List<HomeEntity>?): ImmutableList<HomePresentationEntity>? {
        return homeEntityList?.map {
            it.toHomePresentationEntity()
        }?.toImmutableList()
    }

    private fun HomeEntity.toHomePresentationEntity(): HomePresentationEntity =
        HomePresentationEntity(
            kinopoiskId = this.kinopoiskId,
            nameRu = this.nameRu,
            nameOriginal = this.nameOriginal,
            ratingKinopoisk = this.ratingKinopoisk,
            year = yearToString(this.year),
            posterUrlPreview = this.posterUrlPreview
        )

    private fun yearToString(year: Int?): String =
        if (year != null) year.toString() else ""

}
