package com.practice.feature.auth_impl.data

import com.practice.core.common.db.dao.UserDao
import com.practice.core.common.db.entity.UserEntity
import com.practice.feature.auth_api.repository.AuthRepository
import com.practice.feature.auth_impl.data.datasource.remote.AuthApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val dao: UserDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun sendCodeToUser(phone: String) = withContext(dispatcher) {
        api.sendCodeToUser(phone)
    }

    override suspend fun checkIsCodeCorrect(phone: String, code: String): Boolean =
        withContext(dispatcher) { api.checkIsCodeCorrect(phone, code) }

    override suspend fun getUser(): String? = withContext(dispatcher) {
        dao.getUser()?.phone
    }

    override suspend fun saveUser(phone: String) = withContext(dispatcher) {
        dao.save(UserEntity(phone))
    }
}
