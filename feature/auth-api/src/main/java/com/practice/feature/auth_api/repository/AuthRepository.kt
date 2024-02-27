package com.practice.feature.auth_api.repository

interface AuthRepository {

    suspend fun sendCodeToUser(phone: String)

    suspend fun checkIsCodeCorrect(phone: String, code: String): Boolean

    suspend fun getUser(): String?

    suspend fun saveUser(phone: String)
}
