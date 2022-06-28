package com.example.datalib.domain.repositories.videoList

import com.example.datalib.data.model.PexelsVideoResponse
import com.example.datalib.data.service.VideosService
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import com.example.datalib.utils.processResponse
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(
    private val dataSource: VideosService
) : VideoRepo {
    override suspend fun getVideos(): Either<PexelsVideoResponse, Failure> {
        return processResponse {
            dataSource.getAllVideos()
        }
    }
}