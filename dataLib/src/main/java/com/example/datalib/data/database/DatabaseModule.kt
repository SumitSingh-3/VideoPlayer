package com.example.datalib.data.database

import android.content.Context
import androidx.room.Room
import com.example.datalib.data.dao.VideoHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun getAppDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "history-database"
        ).build()
    }

    @Provides
    fun getArticlesDao(
        roomDatabase: AppDatabase
    ): VideoHistoryDao {
        return roomDatabase.videoHistoryDao()
    }


}