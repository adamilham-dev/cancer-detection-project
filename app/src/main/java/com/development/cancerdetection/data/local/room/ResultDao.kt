package com.development.cancerdetection.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.development.cancerdetection.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultEntity: ResultEntity)

    @Query("SELECT * FROM resultentity ORDER BY createdAt DESC")
    fun getAllResults(): Flow<List<ResultEntity>>

    @Delete
    suspend fun deleteResult(resultEntity: ResultEntity)
}