package com.example.datalib.data.service

import com.example.datalib.data.model.PexelsVideoResponse
import retrofit2.http.GET

interface VideosService {

    @GET("$V1$VIDEOS$POPULAR")
    suspend fun getAllVideos(): PexelsVideoResponse

    companion object {
        const val V1 = "/v1"
        const val POPULAR = "/popular"
        const val VIDEOS = "/videos"
    }

}