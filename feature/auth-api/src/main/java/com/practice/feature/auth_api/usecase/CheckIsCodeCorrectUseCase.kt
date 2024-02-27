package com.practice.feature.auth_api.usecase

interface CheckIsCodeCorrectUseCase {

    suspend operator fun invoke(phone: String, code: String): Boolean
}
