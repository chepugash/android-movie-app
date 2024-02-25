package com.practice.feature.detail.presentation.mapper

import com.practice.feature.detail.presentation.model.DetailPresentationEntity
import com.practice.feature.detail_api.model.CountryEntity
import com.practice.feature.detail_api.model.DetailFilmEntity
import com.practice.feature.detail_api.model.GenreEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

object DetailEntityToDetailPresentationMapper {

    private const val MINUTES_IN_HOUR = 60
    private const val DIGITS_COUNT = 10
    private const val NOT_PROVIDED_MESSAGE = "не указано"
    private const val NO_DESCRIPTION_MESSAGE = "без описания"

    operator fun invoke(entity: DetailFilmEntity): DetailPresentationEntity {
        return DetailPresentationEntity(
            countries = countriesEntityToPresentation(entity.countries),
            description = entity.description ?: NO_DESCRIPTION_MESSAGE,
            filmLength = filmLengthToPresentation(entity.filmLength),
            genres = genresEntityToPresentation(entity.genres),
            kinopoiskId = entity.kinopoiskId,
            nameOriginal = entity.nameOriginal,
            nameRu = entity.nameRu,
            posterUrl = entity.posterUrl,
            ratingAgeLimits = entity.ratingAgeLimits ?: NOT_PROVIDED_MESSAGE,
            ratingImdb = entity.ratingImdb,
            ratingKinopoisk = entity.ratingKinopoisk,
            slogan = entity.slogan,
            webUrl = entity.webUrl,
            year = entity.year?.toString() ?: ""
        )
    }

    private fun genresEntityToPresentation(
        list: List<GenreEntity?>?
    ): ImmutableList<String>? = list?.filter {
        it?.toString() != null
    }?.map {
        it?.genre.toString()
    }?.toImmutableList()

    private fun countriesEntityToPresentation(
        list: List<CountryEntity?>?
    ): ImmutableList<String>? = list?.filter {
        it?.toString() != null
    }?.map {
        it?.country.toString()
    }?.toImmutableList()

    private fun filmLengthToPresentation(
        filmLength: Int?
    ): String {
        return if (filmLength != null) {
            val hours = filmLength / MINUTES_IN_HOUR
            val minutes = filmLength % MINUTES_IN_HOUR

            val hoursString = if (hours < DIGITS_COUNT) "0$hours" else "$hours"
            val minutesString = if (minutes < DIGITS_COUNT) "0$minutes" else "$minutes"

            "$hoursString:$minutesString"
        } else {
            NOT_PROVIDED_MESSAGE
        }
    }
}
