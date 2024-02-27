package com.practice.feature.detail.di

import com.practice.feature.detail.data.DetailRepositoryImpl
import com.practice.feature.detail.data.datasource.remote.DetailApi
import com.practice.feature.detail.data.datasource.remote.DetailResponseToDetailEntityMapper
import com.practice.feature.detail.domain.usecase.GetFilmByIdUseCaseImpl
import com.practice.feature.detail.presentation.mapper.DetailEntityToDetailPresentationMapper
import com.practice.feature.detail.presentation.mvi.DetailViewModel
import com.practice.feature.detail_api.repository.DetailRepository
import com.practice.feature.detail_api.usecase.GetFilmByIdUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val detailModule = module {

    viewModel<DetailViewModel> {
        DetailViewModel(
            getFilmByIdUseCase = get(),
            mapper = get()
        )
    }

    single {
        provideDetailApi(
            retrofit = get()
        )
    }

    single<DetailRepository> {
        DetailRepositoryImpl(
            api = get(),
            responseMapper = get()
        )
    }

    factory<GetFilmByIdUseCase> {
        GetFilmByIdUseCaseImpl(
            repository = get()
        )
    }

    factory<DetailResponseToDetailEntityMapper> {
        DetailResponseToDetailEntityMapper
    }

    factory<DetailEntityToDetailPresentationMapper> {
        DetailEntityToDetailPresentationMapper
    }
}

private fun provideDetailApi(
    retrofit: Retrofit
): DetailApi = retrofit.create(DetailApi::class.java)

