package com.practice.feature.detail.data

import com.practice.feature.detail.data.datasource.remote.DetailApi
import com.practice.feature.detail.data.datasource.remote.DetailResponseToDetailEntityMapper
import com.practice.feature.detail_api.model.DetailFilmEntity
import com.practice.feature.detail_api.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepositoryImpl(
    private val api: DetailApi,
    private val responseMapper: DetailResponseToDetailEntityMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailRepository {

    override suspend fun getFilmById(id: Int): DetailFilmEntity = withContext(dispatcher) {
        responseMapper(api.getFilmById(id))
    }
}
