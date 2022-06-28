package com.example.datalib.domain.repositories.videoList

import com.example.datalib.data.model.PexelsVideoResponse
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure

interface VideoRepo {
    suspend fun getVideos(): Either<PexelsVideoResponse, Failure>
}