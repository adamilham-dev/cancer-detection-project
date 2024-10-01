package com.development.cancerdetection.domain.repository

import kotlinx.coroutines.flow.Flow
import com.development.cancerdetection.domain.model.Result

interface ResultRepository {

    suspend fun insertResult(result: Result)

    fun getAllResults(): Flow<List<Result>>

    suspend fun deleteResult(result: Result)
}