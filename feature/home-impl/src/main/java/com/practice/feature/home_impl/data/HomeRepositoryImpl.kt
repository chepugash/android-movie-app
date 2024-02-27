package com.practice.feature.home_impl.data

import com.practice.core.common.db.dao.UserDao
import com.practice.core.common.db.entity.UserEntity
import com.practice.feature.home_api.model.HomeEntity
import com.practice.feature.home_api.repository.HomeRepository
import com.practice.feature.home_impl.data.datasource.remote.HomeApi
import com.practice.feature.home_impl.data.datasource.remote.HomeResponseToHomeEntityMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val api: HomeApi,
    private val dao: UserDao,
    private val responseMapper: HomeResponseToHomeEntityMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): HomeRepository {

    override suspend fun getFilms(): List<HomeEntity>? = withContext(dispatcher) {
        responseMapper(api.getFilms())
    }

    override suspend fun deleteUser(phone: String) = withContext(dispatcher) {
        dao.delete(UserEntity(phone))
    }

    override suspend fun getUser(): String = withContext(dispatcher) {
        dao.getUser()?.phone!!
    }
}
