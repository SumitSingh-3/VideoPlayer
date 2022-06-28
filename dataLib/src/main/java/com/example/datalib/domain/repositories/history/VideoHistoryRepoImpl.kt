package com.example.datalib.domain.repositories.history

import com.example.datalib.data.dao.VideoHistoryDao
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import com.example.datalib.utils.VideoHistoryFilter
import javax.inject.Inject

class VideoHistoryRepoImpl @Inject constructor(
    private val dao: VideoHistoryDao
) : VideoHistoryRepo {

    override suspend fun addUpdateVideoHistory(videoHistory: PexelsVideoMapedData) : Either<Boolean, Failure> {
        return dao.addUpdate(videoHistory)
    }

    override suspend fun deleteVideoHistory(videoHistory: PexelsVideoMapedData): Either<Boolean, Failure> {
        return dao.deleteVideo(videoHistory)
    }

    override suspend fun getVideoHistory(videoHistoryFilter: VideoHistoryFilter): List<PexelsVideoMapedData> {
        return when(videoHistoryFilter) {
            VideoHistoryFilter.LAST_PLAYED -> dao.getMostRecentVideHistory()
            VideoHistoryFilter.MOST_VIEWED -> dao.getMostViewedVideoHistory()
        }
    }

}