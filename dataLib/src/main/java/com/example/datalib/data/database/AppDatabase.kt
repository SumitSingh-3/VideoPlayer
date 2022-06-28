package com.example.datalib.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.datalib.data.dao.VideoHistoryDao
import com.example.datalib.data.model.DateConverter
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.data.model.VideoHistory

@Database(entities = [PexelsVideoMapedData::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoHistoryDao(): VideoHistoryDao
}