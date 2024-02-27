package com.practice.feature.home_impl.di

import com.practice.feature.home_api.repository.HomeRepository
import com.practice.feature.home_api.usecase.GetFilmsUseCase
import com.practice.feature.home_impl.data.HomeRepositoryImpl
import com.practice.feature.home_impl.data.datasource.remote.HomeApi
import com.practice.feature.home_impl.data.datasource.remote.HomeResponseToHomeEntityMapper
import com.practice.feature.home_impl.domain.usecase.GetFilmsUseCaseImpl
import com.practice.feature.home_impl.presentation.mapper.HomeEntityToHomePresentationMapper
import com.practice.feature.home_impl.presentation.mvi.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {

    viewModel<HomeViewModel> {
        HomeViewModel(
            getFilmsUseCase = get(),
            getUserUseCase = get(),
            signOutUseCase = get(),
            mapper = get(),
        )
    }

    single {
        provideHomeApi(
            retrofit = get()
        )
    }

    single<HomeRepository> {
        HomeRepositoryImpl(
            api = get(),
            dao = get(),
            responseMapper = get()
        )
    }

    factory<GetFilmsUseCase> {
        GetFilmsUseCaseImpl(
            repository = get()
        )
    }

    single<HomeResponseToHomeEntityMapper> {
        HomeResponseToHomeEntityMapper()
    }

    single<HomeEntityToHomePresentationMapper> {
        HomeEntityToHomePresentationMapper()
    }
}

private fun provideHomeApi(
    retrofit: Retrofit
): HomeApi = retrofit.create(HomeApi::class.java)

