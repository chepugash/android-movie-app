package com.practice.feature.auth_api.usecase

interface SendCodeToUserUseCase {

    suspend operator fun invoke(phone: String)
}
