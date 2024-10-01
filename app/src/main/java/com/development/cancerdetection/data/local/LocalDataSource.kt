package com.development.cancerdetection.data.local

import com.development.cancerdetection.data.local.entity.ResultEntity
import com.development.cancerdetection.data.local.room.ResultDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val resultDao: ResultDao,
) {

    suspend fun insertResult(resultEntity: ResultEntity) = resultDao.insertResult(resultEntity)

    fun getAllResults() = resultDao.getAllResults()

    suspend fun deleteResult(resultEntity: ResultEntity) = resultDao.deleteResult(resultEntity)
}