package com.practice.feature.auth_impl.data.datasource.remote

interface AuthApi {

    suspend fun sendCodeToUser(phone: String)

    suspend fun checkIsCodeCorrect(phone: String, code: String): Boolean
}
