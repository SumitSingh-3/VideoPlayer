package com.example.datalib.domain.repositories.history

import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import com.example.datalib.utils.VideoHistoryFilter

interface VideoHistoryRepo {

    suspend fun addUpdateVideoHistory(videoHistory: PexelsVideoMapedData) : Either<Boolean, Failure>

    suspend fun deleteVideoHistory(videoHistory: PexelsVideoMapedData) : Either<Boolean, Failure>

    suspend fun getVideoHistory(videoHistoryFilter: VideoHistoryFilter): List<PexelsVideoMapedData>

}