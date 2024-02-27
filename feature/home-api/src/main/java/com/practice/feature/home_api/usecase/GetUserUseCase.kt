package com.practice.feature.home_api.usecase

interface GetUserUseCase {

    suspend operator fun invoke(): String
}
