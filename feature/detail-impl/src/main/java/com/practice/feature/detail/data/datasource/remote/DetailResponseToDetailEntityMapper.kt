package com.practice.feature.detail.data.datasource.remote

import com.practice.feature.detail_api.model.CountryEntity
import com.practice.feature.detail_api.model.DetailFilmEntity
import com.practice.feature.detail_api.model.GenreEntity

object DetailResponseToDetailEntityMapper {

    operator fun invoke(response: DetailResponseEntity): DetailFilmEntity {
        return DetailFilmEntity(
            countries = countriesResponseToEntity(response.countries),
            coverUrl = response.coverUrl,
            description = response.description,
            filmLength = response.filmLength,
            genres = genresResponseToEntity(response.genres),
            kinopoiskId = response.kinopoiskId,
            nameOriginal = response.nameOriginal,
            nameRu = response.nameRu,
            posterUrl = response.posterUrl,
            posterUrlPreview = response.posterUrlPreview,
            ratingAgeLimits = response.ratingAgeLimits,
            ratingFilmCritics = response.ratingFilmCritics,
            ratingImdb = response.ratingImdb,
            ratingKinopoisk = response.ratingKinopoisk,
            ratingMpaa = response.ratingMpaa,
            shortDescription = response.shortDescription,
            slogan = response.slogan,
            type = response.type,
            webUrl = response.webUrl,
            year = response.year
        )
    }

    private fun genresResponseToEntity(
        response: List<GenreResponseEntity?>?
    ): List<GenreEntity?>? = response?.map {
        GenreEntity(genre = it?.genre)
    }

    private fun countriesResponseToEntity(
        response: List<CountryResponseEntity?>?
    ): List<CountryEntity?>? = response?.map {
        CountryEntity(country = it?.country)
    }
}
