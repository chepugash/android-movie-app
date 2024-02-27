package com.practice.feature.auth_api.usecase

interface SaveUserUseCase {

    suspend operator fun invoke(phone: String)
}
