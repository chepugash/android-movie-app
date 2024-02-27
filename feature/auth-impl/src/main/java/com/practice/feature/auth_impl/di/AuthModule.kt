package com.practice.feature.auth_impl.di

import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_api.usecase.CheckIsAuthorizedUseCase
import com.practice.feature.auth_api.usecase.CheckIsCodeCorrectUseCase
import com.practice.feature.auth_api.usecase.SaveUserUseCase
import com.practice.feature.auth_api.usecase.SendCodeToUserUseCase
import com.practice.feature.auth_impl.data.AuthRepositoryImpl
import com.practice.feature.auth_impl.data.datasource.remote.AuthApi
import com.practice.feature.auth_impl.data.datasource.remote.AuthApiImpl
import com.practice.feature.auth_impl.domain.usecase.CheckIsAuthorizedUseCaseImpl
import com.practice.feature.auth_impl.domain.usecase.CheckIsCodeCorrectUseCaseImpl
import com.practice.feature.auth_impl.domain.usecase.SaveUserUseCaseImpl
import com.practice.feature.auth_impl.domain.usecase.SendCodeToUserUseCaseImpl
import com.practice.feature.auth_impl.presentation.confirm.mvi.ConfirmViewModel
import com.practice.feature.auth_impl.presentation.confirm.util.PhoneMapper
import com.practice.feature.auth_impl.presentation.signin.mvi.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel<SignInViewModel> {
        SignInViewModel(
            sendCodeToUserUseCase = get(),
            checkIsAuthorizedUseCase = get()
        )
    }

    viewModel<ConfirmViewModel> {
        ConfirmViewModel(
            checkIsCodeCorrectUseCase = get(),
            sendCodeToUserUseCase = get(),
            saveUserUseCase = get(),
            phoneMapper = get()
        )
    }

    single<AuthApi> {
        AuthApiImpl()
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            api = get(),
            dao = get()
        )
    }

    factory<SendCodeToUserUseCase> {
        SendCodeToUserUseCaseImpl(
            repository = get()
        )
    }

    factory<CheckIsCodeCorrectUseCase> {
        CheckIsCodeCorrectUseCaseImpl(
            repository = get()
        )
    }

    factory<CheckIsAuthorizedUseCase> {
        CheckIsAuthorizedUseCaseImpl(
            repository = get()
        )
    }

    factory<SaveUserUseCase> {
        SaveUserUseCaseImpl(
            repository = get()
        )
    }

    single<PhoneMapper> {
        PhoneMapper()
    }
}
