package com.development.cancerdetection.data.repository

import com.development.cancerdetection.data.local.LocalDataSource
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.domain.repository.ResultRepository
import com.development.cancerdetection.helper.DataMapper
import com.development.cancerdetection.helper.DataMapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : ResultRepository {
    override suspend fun insertResult(result: Result) =
        localDataSource.insertResult(result.toEntity())

    override fun getAllResults(): Flow<List<Result>> = localDataSource.getAllResults().map { results ->
        results.map {
            DataMapper.mapResultEntityToResult(it)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteResult(result: Result) {
        localDataSource.deleteResult(result.toEntity())
    }
}