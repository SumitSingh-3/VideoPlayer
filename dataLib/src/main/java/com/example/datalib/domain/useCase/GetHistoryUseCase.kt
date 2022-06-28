package com.example.datalib.domain.useCase

import com.example.datalib.base.BaseUseCaseUnwrappedWithInput
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.domain.repositories.history.VideoHistoryRepo
import com.example.datalib.utils.VideoHistoryFilter
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repo: VideoHistoryRepo
): BaseUseCaseUnwrappedWithInput<VideoHistoryFilter, List<PexelsVideoMapedData>> {

    override suspend fun process(input: VideoHistoryFilter): List<PexelsVideoMapedData> {
        return repo.getVideoHistory(input)
    }

}