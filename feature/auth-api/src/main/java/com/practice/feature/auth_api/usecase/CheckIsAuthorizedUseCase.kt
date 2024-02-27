package com.practice.feature.auth_api.usecase

interface CheckIsAuthorizedUseCase {

    suspend operator fun invoke(): Boolean
}
