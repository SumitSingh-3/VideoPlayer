package com.example.datalib.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import java.util.*

@Dao
interface VideoHistoryDao {

    @Query("SELECT * FROM pexelsvideohistory")
    fun getEntireVideHistory(): List<PexelsVideoMapedData>

    @Query("SELECT * FROM pexelsvideohistory WHERE id LIKE :videoId")
    suspend fun getVideoHistoryById(videoId: Int): PexelsVideoMapedData?

    @Query("SELECT * FROM pexelsvideohistory ORDER BY last_played_date DESC")
    fun getMostRecentVideHistory(): List<PexelsVideoMapedData>

    @Query("SELECT * FROM pexelsvideohistory ORDER BY play_count DESC")
    fun getMostViewedVideoHistory(): List<PexelsVideoMapedData>

    @Insert(onConflict = REPLACE)
    suspend fun insert(videoHistory: PexelsVideoMapedData)

    @Update
    suspend fun update(videoHistory: PexelsVideoMapedData)

    @Query("DELETE FROM pexelsvideohistory")
    suspend fun deleteAll()

    @Query("DELETE FROM pexelsvideohistory WHERE id = :videoId")
    fun deleteByVideoId(videoId: Int)

    @Transaction
    suspend fun addUpdate(obj: PexelsVideoMapedData): Either<Boolean, Failure> {
        return try {
            val historyItem = getVideoHistoryById(obj.id)
            if (historyItem == null) {
                obj.lastAccessTime = Date()
                obj.viewCount = 1
                insert(obj)
            } else {
                val count = historyItem.viewCount
                obj.viewCount = count + 1
                obj.lastAccessTime = Date()
                update(obj)
            }
            Either.Success(true)
        } catch (ex: Exception) {
            Either.Error(Failure.Default(ex))
        }
    }

    @Transaction
    suspend fun deleteVideo(obj: PexelsVideoMapedData): Either<Boolean, Failure> {
        return try {
            deleteByVideoId(obj.id)
            Either.Success(true)
        } catch (ex: java.lang.Exception){
            Either.Error(Failure.Default(ex))
        }
    }

}