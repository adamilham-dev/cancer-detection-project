package com.development.cancerdetection.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.development.cancerdetection.data.local.entity.ResultEntity
import com.development.cancerdetection.data.local.room.ResultDao

@Database(entities = [ResultEntity::class], version = 1, exportSchema = false)
abstract class ResultDatabase: RoomDatabase() {

    abstract fun resultDao(): ResultDao
}