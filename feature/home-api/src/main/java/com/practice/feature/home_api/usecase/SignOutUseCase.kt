package com.practice.feature.home_api.usecase

interface SignOutUseCase {

    suspend operator fun invoke(phone: String)
}
