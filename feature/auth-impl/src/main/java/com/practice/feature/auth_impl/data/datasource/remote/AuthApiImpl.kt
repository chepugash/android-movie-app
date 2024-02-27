package com.practice.feature.auth_impl.data.datasource.remote

class AuthApiImpl() : AuthApi {

    private var confirmationCode: String? = null

    override suspend fun sendCodeToUser(phone: String) {
        confirmationCode = phone.takeLast(CODE_LENGTH)
    }

    override suspend fun checkIsCodeCorrect(phone: String, code: String): Boolean {
        return code == confirmationCode
    }

    companion object {

        private const val CODE_LENGTH = 6
    }
}
