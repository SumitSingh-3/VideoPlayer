package com.example.datalib.domain.useCase

import com.example.datalib.base.BaseUseCase
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.data.model.PexelsVideoResponse
import com.example.datalib.domain.repositories.videoList.VideoRepoImpl
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import javax.inject.Inject

class GetRemoteVideoListUseCase @Inject constructor(
    private val repo: VideoRepoImpl
) : BaseUseCase<Either<List<PexelsVideoMapedData>, Failure>> {
    override suspend fun process(): Either<List<PexelsVideoMapedData>, Failure> {
        return map(repo.getVideos())
    }

    fun map(response: Either<PexelsVideoResponse, Failure>): Either<List<PexelsVideoMapedData>, Failure> {
        if (response.isError) {
            return Either.Error(response.getError() ?: Failure.Default())
        } else {
            val responseData = response.getSuccess()

            val list = ArrayList<PexelsVideoMapedData>()
            responseData?.videos?.forEach { video ->
                PexelsVideoMapedData(
                    id = video.id,
                    name = video.user?.name,
                    image = video.image,
                    link = if (video.videoFiles?.isNotEmpty() == true) video.videoFiles[0].link else null,
                    duration = video.duration
                ).also { list.add(it) }
            }
            return Either.Success(list.toList())
        }
    }

}